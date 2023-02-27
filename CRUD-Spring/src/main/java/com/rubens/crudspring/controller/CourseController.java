package com.rubens.crudspring.controller;

import com.rubens.crudspring.DTO.CourseDTO;
import com.rubens.crudspring.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/courses") // Essa classe então fica com o end-point acima e tudo nela será renderizado quando o end-point for acessado.
public class CourseController {


  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping //Informa que o método usado será o GET // MESMA COISA DE = @RequestMapping(method = RequestMethod.GET) \\
  public List<CourseDTO> list() {
    return courseService.list();
  }

  @GetMapping("/{id}")
  public CourseDTO buscaId(@PathVariable @NotNull @Positive Long id) {
    return courseService.buscaId(id);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<CourseDTO> salvar(@RequestBody @Valid CourseDTO curso) { //Método para salvar dados no banco de dados do tipo Course e que retornará o HTTP 201 (Created) por conta do ResponseEntity.
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(courseService.salvar(curso));
  }

  //Uma maneira de fazermos resumidamente o ResponseEntity, porém a diferença é que não poderemos alterar nem manusear os dados da reposta.
  /*@PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Course salvar(@RequestBody Course curso) {
    return coursesRepository.save(curso);
  }*/

  @PutMapping("/{id}")
  public CourseDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull CourseDTO curso) {
    return courseService.update(id, curso); // Está verificando se o curso existe buscando por id.
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable @NotNull @Positive  Long id) {
    courseService.delete(id);
  }
}
