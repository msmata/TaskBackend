package com.msmata.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.msmata.task.config.JpaConfiguration;


@SpringBootApplication(scanBasePackages={"com.msmata.task"})
@Import(JpaConfiguration.class)
public class TaskApiApp {

	public static void main(String[] args) {
		SpringApplication.run(TaskApiApp.class, args);
	}
}
