package hw1.roadroam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "hw1.roadroam")
public class Ex2Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex2Application.class, args);
	}

}
