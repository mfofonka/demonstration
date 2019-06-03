package com.mfofonka.demonstration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemonstrationApplication {

	public static void main(String[] argv) {
		SpringApplication.run(DemonstrationApplication.class, argv);
	}
}
