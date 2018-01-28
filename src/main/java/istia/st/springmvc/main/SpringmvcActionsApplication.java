package istia.st.springmvc.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"istia.st.springmvc.controllers", "istia.st.springmvc.models"})
@EnableAutoConfiguration
public class SpringmvcActionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmvcActionsApplication.class, args);
	}
}
