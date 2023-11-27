package com.example.escolacursosjava;

import com.example.escolacursosjava.model.Course;
import com.example.escolacursosjava.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EscolaCursosJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EscolaCursosJavaApplication.class, args);

    }

    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository){
        return  args -> {
            courseRepository.deleteAll();

            Course c = new Course();
            c.setName("Angular com Spring");
            c.setCategory("front-end");

            courseRepository.save(c);
        };
    }

}
