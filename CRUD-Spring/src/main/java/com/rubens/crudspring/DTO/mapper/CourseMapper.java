package com.rubens.crudspring.DTO.mapper;

import com.rubens.crudspring.DTO.CourseDTO;
import com.rubens.crudspring.DTO.LessonsDTO;
import com.rubens.crudspring.enums.Category;
import com.rubens.crudspring.model.Course;
import com.rubens.crudspring.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

  public CourseDTO toDTO(Course course) { // Método que transforma uma entidade Course em uma DTO.
    if (course == null) {
      return null;
    }
    List<LessonsDTO> aulas = course.getLessons().stream() // Estamos pegando as lessons e fazendo um stream com elas
      .map(lessons -> new LessonsDTO(lessons.getId(), lessons.getName(), lessons.getYoutubeUrl()))  // Estamos a cada lesson criando um LessonDTo com as mesmas propriedades.
      .collect(Collectors.toList()); // Coletamos o que foi criado acima e passamos para a lista.
    return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), aulas);
  }

  public Course toEntity(CourseDTO courseDTO) { // Método que transforma um CourseDTO em uma entidade Course.
    if (courseDTO == null) {
      return null;
    }

    Course course = new Course();
    if (courseDTO.id() != null) {
      course.setId(courseDTO.id());
    }

    course.setName(courseDTO.name());
    course.setCategory(convertCategoryValue(courseDTO.category()));

    return course;
  }

  public Category convertCategoryValue(String value) {  // Método que Converte a String recebida da categoria para o enumerador correto.
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "Front-end" -> Category.FRONTEND;
      case "Back-end" -> Category.BACKEND;
      default -> throw new IllegalArgumentException("Categoria inválida: " + value);
    };
  }

}
