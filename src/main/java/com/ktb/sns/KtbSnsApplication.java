package com.ktb.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KtbSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KtbSnsApplication.class, args);
	}

}
