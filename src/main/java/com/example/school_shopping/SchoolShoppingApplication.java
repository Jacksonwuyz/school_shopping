package com.example.school_shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.example.school_shopping.dao")
@SpringBootApplication
public class SchoolShoppingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SchoolShoppingApplication.class, args);
	}

}
