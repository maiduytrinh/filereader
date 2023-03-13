package com.tp.filereader.listener;


import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.ringtone.token.Token;
import com.tp.filereader.Server;
import com.tp.filereader.Utils;
import com.tp.filereader.director.service.DirectoryWatchService.OnFileChangeListener;
import com.tp.filereader.logger.TPLogger;
import com.tp.filereader.service.StatusService;

/**
 * Created by The TP Entertainment
 * Author : Vu Duy Tu
 *          duytucntt@gmail.com
 * Dec 29, 2015  
 */
public class NameUpdaterOnFileChangeListener implements OnFileChangeListener {
	private static final Logger LOG = TPLogger.getLogger(NameUpdaterOnFileChangeListener.class);
	private static long lastBandWidth = 0l;

	private StatusService statusService;

	public NameUpdaterOnFileChangeListener() {
		String watchDir = System.getProperty("watched.dir", Server.WATCHED_DIR);
		try {
			String filePath = watchDir + "/lastbandwidth.txt";
			File f = new File(filePath);
			if (f.exists()) {
				String info = Utils.readFirstLine(f);
				if (info != null && !info.trim().isEmpty() && !"0".equals(info)) {
					lastBandWidth = Long.valueOf(info.trim());
					LOG.info("Init lastBandWidth: " + lastBandWidth);
				}
			}
		} catch (Exception e) {
			LOG.error("Initilization NameUpdaterOnFileChangeListener error: ", e, false);
		}
		//
		Token.loadConfig(watchDir + "/" + "dataAppId.txt");
	}

	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
		//
		loadServerStatus();
	}

	@Override
	public void onFileCreate(String filePath) {
		setServerStatus(filePath, "cao");
	}

	@Override
	public void onFileDelete(String filePath) {
		setServerStatus(filePath, "");
	}

	private void setServerStatus(String filePath, String status) {
		try {
			String serverName = filePath.substring(filePath.lastIndexOf("/") + 1);
			if (StringUtils.isNumeric(serverName)) {
				statusService.setServerStatus(Integer.valueOf(serverName), status);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e, false);
		}
	}

	@Override
	public void onFileModify(String filePath) {
		try {
			String watchDir = System.getProperty("watched.dir", Server.WATCHED_DIR);
			if (filePath.contains("dataAppId")) {
				Token.loadConfig(watchDir + "/" + filePath);
			}
			if(System.getProperty("supportBee", "").equals("true")) {
				String svInfo = System.getProperty("mainServer", "false");
				if ("true".equals(svInfo) && filePath.contains("lastbandwidth")) {
					filePath = watchDir + "/" + filePath;
					String info = Utils.readFirstLine(new File(filePath));
					if(info != null && !info.trim().isEmpty()) {
						lastBandWidth = Long.valueOf(info.trim());
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e, false);
		}
	}

	private void loadServerStatus() {
		try {
			if (!StringUtils.isEmpty(Server.WATCHED_DIR) && new File(Server.WATCHED_DIR).isDirectory()) {
				File file[] = new File(Server.WATCHED_DIR).listFiles();
				for (File f : file) {
					if (StringUtils.isNumeric(f.getName())) {
						statusService.setServerStatus(Integer.valueOf(f.getName()), "cao");
					}
				}
			}
			//
		} catch (Exception e) {
			LOG.error("Error loadServerStatus ", e);
		}
	}

}
