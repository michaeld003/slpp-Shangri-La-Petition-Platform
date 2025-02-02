package com.shangri_la.slpp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("com.shangri_la.slpp.entity")
@EnableJpaRepositories("com.shangri_la.slpp.repository")
public class SlppShangriLaPetitionPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlppShangriLaPetitionPlatformApplication.class, args);
    }

}
