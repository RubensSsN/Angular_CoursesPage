package com.rubens.crudspring.DTO.mapper;

import com.rubens.crudspring.DTO.CourseDTO;
import com.rubens.crudspring.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

  public CourseDTO toDTO(Course course) { // Método que transforma uma entidade Course em uma DTO.
    if (course == null) {
      return null;
    }
    return new CourseDTO(course.getId(), course.getName(), course.getCategory());
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
    course.setCategory(courseDTO.category());

    return course;
  }

}