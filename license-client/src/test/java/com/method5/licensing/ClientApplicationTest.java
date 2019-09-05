package com.method5.licensing;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class ClientApplicationTest {
    public static void main(String[] args) {

        String[] appArgs = {"--debug"};
        SpringApplication app = new SpringApplication(ClientApplicationTest.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(appArgs);
    }
}
