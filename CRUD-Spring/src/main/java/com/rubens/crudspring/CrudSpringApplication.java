package com.rubens.crudspring;

import com.rubens.crudspring.enums.Category;
import com.rubens.crudspring.model.Course;
import com.rubens.crudspring.model.Lesson;
import com.rubens.crudspring.repository.CoursesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class CrudSpringApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrudSpringApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrudSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner initDataBase(CoursesRepository coursesRepository) {
      return args -> {
        coursesRepository.deleteAll();

        Course c = new Course();
        c.setName("Angular com Spring");
        c.setCategory(Category.FRONTEND);


        Lesson l = new Lesson();
        l.setName("Astronomia");
        l.setYoutubeUrl("Acadovski");
        l.setCourse(c);

        c.getLessons().add(l);

        try {
          coursesRepository.save(c);
          LOGGER.info("[RESPONSE] Curso salvo com sucesso - {}", c.getName());
        } catch (Exception e) {
          LOGGER.error("[ERRO] Salvar curso - {}", e.getMessage());
        }
      };
    }

}
