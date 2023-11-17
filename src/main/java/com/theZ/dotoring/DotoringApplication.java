package com.theZ.dotoring;

import com.theZ.dotoring.config.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:env/env.yaml", factory = EnvConfig.class)
public class DotoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotoringApplication.class, args);
	}

}
