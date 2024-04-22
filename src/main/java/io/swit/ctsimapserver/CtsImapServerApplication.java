package io.swit.ctsimapserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CtsImapServerApplication {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:env.yml,"
            + "classpath:application.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(CtsImapServerApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
