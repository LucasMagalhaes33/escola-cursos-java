package com.example.escolacursosjava;

import com.example.escolacursosjava.enums.Category;
import com.example.escolacursosjava.model.Course;
import com.example.escolacursosjava.model.Lesson;
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
    CommandLineRunner initDatabase(CourseRepository courseRepository) {
        return args -> {
            courseRepository.deleteAll();

            for (int i = 0; i < 20; i++) {

                Course c = new Course();
                c.setName("Angular com Spring " + i);
                c.setCategory(Category.BACK_END);

                Lesson l = new Lesson();
                l.setName("Introdução");
                l.setYoutubeUrl("01234567890");
                l.setCourse(c);
                c.getLessons().add(l);

                Lesson l1 = new Lesson();
                l1.setName("Angular");
                l1.setYoutubeUrl("01234567891");
                l1.setCourse(c);
                c.getLessons().add(l1);

                courseRepository.save(c);
            }
        };
    }

}