package com.alediesme.joyeria.shared.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class LoggingStartupVerifier {

  private static final Logger log = LoggerFactory.getLogger(LoggingStartupVerifier.class);

  @EventListener(ApplicationReadyEvent.class)
  public void logStartup(ApplicationReadyEvent event) {
    Environment environment = event.getApplicationContext().getEnvironment();
    String logPath = environment.getProperty("logging.file.path", "./logs");
    String[] profiles = environment.getActiveProfiles();

    log.info(
        "Application ready. profiles={} logFile={}/alediesme-application.log",
        profiles.length == 0 ? "default" : String.join(",", profiles),
        logPath);
  }
}
