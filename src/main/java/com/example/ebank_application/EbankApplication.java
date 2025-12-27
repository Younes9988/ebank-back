package com.example.ebank_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("amine123"));
	}

}
