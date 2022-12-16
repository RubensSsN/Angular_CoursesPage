package com.rubens.crudspring.controller;

import com.rubens.crudspring.model.Course;
import com.rubens.crudspring.repository.CoursesRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/courses") // Essa classe então fica com o end-point acima e tudo nela será renderizado quando o end-point for acessado.
public class CourseController {

  private final CoursesRepository coursesRepository;

  public CourseController(CoursesRepository coursesRepository) {
    this.coursesRepository = coursesRepository;
  }

  @GetMapping //Informa que o método usado será o GET // MESMA COISA DE = @RequestMapping(method = RequestMethod.GET) \\
  public List<Course> list() {
    return coursesRepository.findAll();
  }

  @PostMapping
  @Transactional
  public Course salvar(@RequestBody Course curso) {
    return coursesRepository.save(curso);
  }

}
