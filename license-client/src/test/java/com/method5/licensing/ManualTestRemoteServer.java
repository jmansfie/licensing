package com.method5.licensing;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = { "com.method5.licensing.service" })
@PropertySource(value={"classpath:remote.properties"}, ignoreResourceNotFound = true)
@Configuration
public class ManualTestRemoteServer {
    public static void main(String[] args) {

        String[] appArgs = {""};
        SpringApplication app = new SpringApplication(ManualTestRemoteServer.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(appArgs);
    }
}
