package com.tp.filereader;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tp.filereader.common.config.AppConfiguration;
import com.tp.filereader.common.config.ConfigurationChangeListner;
import com.tp.filereader.index.IndexingService;
import com.tp.filereader.rdbms.EntityManagerHelper;
import com.tp.filereader.rest.dto.VersionDto;
import com.tp.filereader.service.DocumentService;
import com.tp.filereader.service.impl.DocumentServiceImpl;
import com.tp.filereader.utils.FileHelper;
import com.tp.filereader.utils.VersionUtils;
import com.zandero.cmd.CommandLineException;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;

/**
 * Server set up ... and start up
 */
public class Server {

	private static final Logger LOG = LoggerFactory.getLogger(Server.class);
	public static String WATCHED_DIR = "/opt/lampp/htdocs/server_stt";// server_stt
	private static final String FILE_PATH = "config.properties";

	ServerSettings settings = new ServerSettings();

	public static void main(String[] args) {
		new Server().run(args);
	}

	public void run(String[] args) {
		loadProperties();

		try {

			// get settings from command line
			settings.parse(args);

			// show version only?
			if (settings.showVersion()) {
				showVersion();
				return;
			}

			if (settings.showHelp()) {
				showHelp(settings.getHelp());
				return;
			}

			// get pool size
			int poolSize = settings.getPoolSize();
			long maxExecuteTime = 120000;

			VertxOptions opts = new VertxOptions();

			opts.setMetricsOptions(
					new DropwizardMetricsOptions().setEnabled(true).setJmxEnabled(true).setJmxDomain("tp-metrics"));

			// check for blocked threads every 5s
			opts.setBlockedThreadCheckInterval(600);
			opts.setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS);

			// log the stack trace if an event loop or worker handler took more than 20s to
			// execute
			opts.setWarningExceptionTime(5);
			opts.setWarningExceptionTimeUnit(TimeUnit.MINUTES);

			// deploy verticle
			Vertx vertx = Vertx.vertx(opts);

			DeploymentOptions options = new DeploymentOptions();
			options.setWorkerPoolSize(poolSize);
			options.setMaxWorkerExecuteTime(maxExecuteTime);
			options.setWorkerPoolName("tp.worker.pool");

			vertx.deployVerticle(new ServerVerticle(settings), options, res -> {
				if (res.failed()) {
					LOG.error("Failed to start server", res.cause());
				} else {

					vertx.executeBlocking(fu -> {
						EntityManagerHelper.init();
						IndexingService.initKeyword();

						fu.complete("Success");
					}, handler -> {
						if (handler.succeeded()) {
							LOG.info("Initialization data is completed!");
						} else {
							LOG.error(handler.cause().getMessage(), handler.cause());
						}
					});

				}

				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						System.out.println("Undeploy the vertx verticle and safe exit the server!");
						vertx.undeploy(res.result(), res -> {
							if (res.succeeded()) {
								vertx.close();
								System.exit(NORM_PRIORITY);
							}
						});
					}
				});
			});
		} catch (CommandLineException e) {
			// some command line setting was invalid ... show help
//			LOG.error("Failed to get settings: ", e);
//
//			showVersion();
//			showHelp(settings.getHelp());
		}

		// 10 giây lưu list record 1 lần
		DocumentService documentService = new DocumentServiceImpl();
		documentService.start();

	}

	private void showHelp(List<String> help) {
		for (String item : help) {
			System.out.println(item);
		}

		System.out.print(System.lineSeparator());
	}

	private void showVersion() {
		VersionDto version = VersionUtils.version();
		System.out.println(String.format("Rest.vertx TP API service: %s, %s", version.getVersion(), version.getDate()));
		System.out.print(System.lineSeparator());
	}

	private void loadProperties() {
		File file = new File(FILE_PATH);
		boolean exists = FileHelper.exists(file);

		if (exists) {
			LOG.info("Loaded config.properties with hot reload at " + FileHelper.getAbsolutePath(file));
			ConfigurationChangeListner listner = new ConfigurationChangeListner(FileHelper.getAbsolutePath(file));
			try {
				new Thread(listner).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			LOG.info("Loaded config.properties default in jar without hot reload.");
			AppConfiguration.initilize(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_PATH));

		}
	}
}
