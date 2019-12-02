package com.tty.twsearch;

import com.tty.twsearch.controller.MainController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.tty.twsearch"})
@MapperScan("com.tty.twsearch.mapper")
@ComponentScan("com.tty.twsearch.*")
@ComponentScan(basePackageClasses= MainController.class)
public class TwSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwSearchApplication.class, args);
	}

}
