package org.vazhenin.xmlcalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class XmlcalendarApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmlcalendarApplication.class, args);
    }

}
