package com.tp.filereader;
import static com.github.pemistahl.lingua.api.Language.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import com.google.common.collect.Lists;
import com.google.common.io.FileWriteMode;
import com.tp.filereader.logger.TPLogger;

/**
 * Created by The TP Entertainment 
 * Author : Dao Minh Khoa minhkhoa.dao@tp.com.vn
 * Mon 06 03 2023
 */
public class Utils {
	private static final Logger LOG = TPLogger.getLogger(Utils.class);
	public static List<String> supportedCountries = Lists.newArrayList("KR","VN","US");
	public static String CJK_LANG_COUNTRIES = "ja,jp,ko,kr";
	public static String specialCountries = "EG,JP,KR".toLowerCase();
	public static String specialLanguages = "ar,ja,ko".toLowerCase();
	private static final String[] REMOVED = "`,.,|,!,#,$,%,^,&,_,=,-,+,*,@,~,[,],{,},(,),\\,\",<,>,?,/,:,;".split(",");
	static LanguageDetector langDetector = null;
	private static final FastDateFormat dateFormatReturn = FastDateFormat.getInstance("EEE, dd MMM yyyy HH:mm:ss z", TimeZone.getTimeZone("GMT"));
	
	@SuppressWarnings("serial")
	public static Map<String, String> mapLanguage = new HashMap<String, String>() {
		{
			put("EG", "ar");
			put("JP", "ja");
			put("KR", "ko");
			put("TW", "zh");
			put("RU", "ru");
			put("UA", "uk");
			put("TH", "th");
			put("BD", "bn");
		}
	};
	
	public static void writeAppend(String filePath, CharSequence line) {
		writeAppend(new File(filePath), line);
	}

	public static void writeAppend(File file, CharSequence line) {
		try {
			if (!file.exists()) {
				file.createNewFile();
				file.setWritable(true);
			}
			com.google.common.io.Files.asCharSink(file, Charset.defaultCharset(), FileWriteMode.APPEND).write(line + "\n");
		} catch (Exception e) {
			LOG.error("writeAppend error: file " + file.getPath(), e, false);
		}
	}

	public static void sendOnThreadReportServerStatus(final int status) {
		sendOnThreadReportServerStatus(status, "cao");
	}

	public static void sendOnThreadReportServerStatus(final int status, final String type) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				sendReportServerStatus(status, type);
			}
		});
		t.setName("ThreadReportServer");
		t.start();
	}

	public static void sendReportServerStatus(int status, String type) {
		try {
			//
			if ("true".equals(System.getProperty("reportServer", "true"))) {
				LOG.info("sendReportServerStatus ...");
				//
				String home = System.getProperty("user.home", "/home/ubuntu");
				String port = System.getProperty("port", "8282");
				if (port.length() > 2) {
					port = port.substring(0, 2);
				}
				String endIP = getSIPFromFile(new File(home + "/status/SIP"));
				// send report
				String url = new StringBuilder("http://www.wallstorage.net/infoserver/hight-cpu.php?server=")
						.append(System.getProperty("serverCode", "8")).append(status).append(endIP).append(port).append("&type=")
						.append(type).toString();
				List<String> rs = executeCommandOut("curl -s -k '" + url + "'");
				LOG.info("sendReportServerStatus " + url + " result " + rs);
			}

		} catch (Exception e) {
			LOG.error("sendReportServerStatus error: ", e, false);
		}
	}
	
	public static String getCountryBySupported(String lang) {
	  String country = getCountry(lang);
	  if (supportedCountries.contains(country.toUpperCase())) {
	    return country;
	  }
	  return "OT";
	}

	public static String readFirstLine(File file) {
		try {
			return com.google.common.io.Files.asCharSource(file, Charset.defaultCharset()).readFirstLine();
		} catch (Exception e) {
			LOG.error("readFirstLine error: ", e, false);
			return "";
		}
	}

	public static String getSIPFromFile(File file) {
		try {
			if (file.getName().equals("SIP")) {
				String endIP = readFirstLine(file);
				if (!StringUtils.isEmpty(endIP)) {
					return endIP;
				}
			}
		} catch (Exception e) {
			LOG.error("getSIPFromFile error: ", e, false);
		}
		return "SIP";
	}

	public static List<String> executeCommandOut(CharSequence command) {
		try {
			LOG.info("exec: " + command);
			String[] cmds = { "/bin/sh", "-c", command.toString() };
			//
			Process proc = Runtime.getRuntime().exec(cmds);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			// read the output from the command
			List<String> result = new ArrayList<>();
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				if (s.trim().length() > 0) {
					result.add(s.trim());
				}
			}
			// read any errors from the attempted command
			boolean first = true;
			while ((s = stdError.readLine()) != null) {
				if (s.isEmpty()) {
					continue;
				}
				if (first) {
					first = false;
					LOG.info("Here is the standard error of the command (if any):\n");
				}
				LOG.info(s);
			}
			return result;
		} catch (Exception e) {
			LOG.error("Error executeCommandOut", e);
		}
		return new ArrayList<>();
	}

	public static String getCountry(String fullLang) {
		if (fullLang != null && !fullLang.isEmpty()) {
			fullLang = fullLang.replace("-", "_");
			if (fullLang.contains("_")) {
				String info[] = fullLang.split("_");
				if (info.length > 1 && info[1].length() == 2) {
					return (info[1]).toUpperCase();
				}
				return (info[0]).toUpperCase();
			}
			return fullLang.toUpperCase();
		}
		return "OT";
	}

	public static String getQueryInput(String in) {
		return getQueryInput(in, true);
	}

	public static String getQueryInput(String in, boolean javaEscape) {
		return getQueryInput(in, "us", javaEscape);
	}

	public static String getQueryInput(String in, String country, boolean javaEscape) {
		if (in == null || in.trim().isEmpty()) {
			return "";
		}
		String out = urlDecoder(in);
		if (out.length() > 0) {
			out = unAccent(out).toLowerCase();
			for (int i = 0; i < REMOVED.length; i++) {
				out = StringUtils.replace(out, REMOVED[i], " ");
			}
			out = StringUtils.replace(out, ",", " ");
			while (out.contains("  ")) {
				out = StringUtils.replace(out, "  ", " ");
			}
		}
		if (javaEscape) {
			out = StringEscapeUtils.escapeJava(out);
			out = StringUtils.replace(out, "\\u", "").toLowerCase();
		}
		if (!"ru,tw,jp".contains(country.toLowerCase())) {
			out = out.replace("0307", "").replace("0308", "");
		}
		return out.trim().replace("0300", "").replace("0301", "").replace("0302", "").replace("0303", "")
				.replace("0309", "").replace("031b", "").replace("0323", "");
	}

	public static String unAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
	}

	public static String urlEncoder(String in) {
		try {
			return URLEncoder.encode(urlDecoder(in), "UTF-8").replace("+", "%20");
		} catch (Exception e) {
			return in;
		}
	}
	
	public static byte[] compress(final byte[] input) {
		try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
				GZIPOutputStream gzipper = new GZIPOutputStream(bout)) {
			gzipper.write(input, 0, input.length);
			gzipper.close();
			return bout.toByteArray();
		} catch (Exception e) {
			return new ByteArrayOutputStream().toByteArray();
		}
	}
	
	public static byte[] uncompress(final byte[] input) throws Exception {
		try (ByteArrayOutputStream bout = new ByteArrayOutputStream();
				GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(input))) {
			byte[] buffer = new byte[1024];
			int len;
			while ((len = gis.read(buffer)) > 0) {
				bout.write(buffer, 0, len);
			}
			gis.close();
			return bout.toByteArray();
		}
	}

	public static String urlDecoder(String in) {
		try {
			String out = URLDecoder.decode(in, "UTF-8").replace("+", " ").trim();
			while (out.contains("  ")) {
				out = out.replace("  ", " ");
			}
			return out;
		} catch (Exception e) {
			return in;
		}
	}
	
	public static void loadLanguageModels() {
		langDetector = LanguageDetectorBuilder.fromLanguages(ARABIC, ENGLISH, KOREAN, JAPANESE, CHINESE, RUSSIAN, THAI, UKRAINIAN, PERSIAN, URDU, BENGALI).build();
	}
	
	public static String langDetect(String text) {
		try {
			if (StringUtils.isEmpty(text)) {
				return "en";
			}
			Language detectedLanguage = langDetector.detectLanguageOf(text);
			if (detectedLanguage == Language.UNKNOWN) {
				return "en";
			}
			return detectedLanguage.getIsoCode639_1().toString();
		} catch (Exception e) {
			LOG.error("Detect Language failed", e);
		}
		return "en";
	}
	
	public static String convertLangSpecial(String lang, String country) {
		if(specialCountries.contains(country.toLowerCase())) {
			lang = mapLanguage.get(country.toUpperCase());
		}
		return lang;
	}
	
	public static String getServerTime() {
		return dateFormatReturn.format(System.currentTimeMillis());
	}
}
