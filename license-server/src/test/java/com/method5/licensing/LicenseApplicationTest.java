package com.method5.licensing;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = { "com.method5.licensing.controller", "com.method5.licensing.service", "com.method5.licensing.config", "com.method5.licensing.dao" })
@Configuration
public class LicenseApplicationTest {
    public static void main(String[] args) {

        String[] appArgs = {"--debug"};
        SpringApplication app = new SpringApplication(LicenseApplicationTest.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(appArgs);
    }
}
