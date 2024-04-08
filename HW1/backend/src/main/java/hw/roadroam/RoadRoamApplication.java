package hw.roadroam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "hw.roadroam")
public class RoadRoamApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoadRoamApplication.class, args);
	}

}
