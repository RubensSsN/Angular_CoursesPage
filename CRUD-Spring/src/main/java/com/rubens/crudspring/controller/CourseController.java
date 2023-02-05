package com.rubens.crudspring.controller;

import com.rubens.crudspring.model.Course;
import com.rubens.crudspring.repository.CoursesRepository;
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
  public List<Course> list() {
    return courseService.list();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Course> buscaId(@PathVariable @NotNull @Positive Long id) {
    return courseService.buscaId(id)
      .map(recordFound -> ResponseEntity.ok().body(recordFound)) //Se nosso optional trouxer uma informaÇão do banco de dados iremos retornar isso no corpo da requisição conforme pedido.
      .orElse(ResponseEntity.notFound().build()); // Se não encontrar iremos fazer o retorno de 404 dizendo que não foi encontrado o registro.
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<Course> salvar(@RequestBody @Valid Course curso) { //Método para salvar dados no banco de dados do tipo Course e que retornará o HTTP 201 (Created) por conta do ResponseEntity.
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
  public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course curso) {
    return courseService.update(id, curso) // Está verificando se o curso existe buscando por id.
      .map(recordFound -> ResponseEntity.ok().body(recordFound)) // Se encontrado o valor ele irá passar para o corpo do site o valor e um http status 200 (ok).
      .orElse(ResponseEntity.notFound().build()); // Se não encontrar iremos fazer o retorno de 404 dizendo que não foi encontrado o registro.
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive  Long id) {
    if (courseService.delete(id)) {
      return ResponseEntity.noContent().<Void>build();
    }
    return ResponseEntity.notFound().build();
  }

}
