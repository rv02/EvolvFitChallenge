package com.rv02.evolvFit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {BlogRepository.class,
		CommentRepository.class, ParentChildCommentRepository.class})
public class EvolvFitApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvolvFitApplication.class, args);
	}

}
