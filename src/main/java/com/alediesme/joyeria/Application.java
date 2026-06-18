package com.alediesme.joyeria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.alediesme.joyeria"})
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  public static void main(String[] args) {
    if (!isRunningInWebLogic()) {
      SpringApplication.run(Application.class, args);
    }
  }

  private static boolean isRunningInWebLogic() {
    return System.getProperty("weblogic.Name") != null;
  }
}
