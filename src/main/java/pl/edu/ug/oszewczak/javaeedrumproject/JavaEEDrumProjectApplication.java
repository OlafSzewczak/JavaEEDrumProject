package pl.edu.ug.oszewczak.javaeedrumproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.ug.oszewczak.javaeedrumproject.service.DrumService;

@SpringBootApplication
public class JavaEEDrumProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaEEDrumProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUpApp(@Autowired DrumService drumService) {
        return (args) -> drumService.initializeDatabase();
    }

}
