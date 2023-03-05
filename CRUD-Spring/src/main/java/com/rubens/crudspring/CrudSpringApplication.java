package com.rubens.crudspring;

import com.rubens.crudspring.enums.Category;
import com.rubens.crudspring.model.Course;
import com.rubens.crudspring.repository.CoursesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudSpringApplication {

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

        coursesRepository.save(c);
      };
    }

}
