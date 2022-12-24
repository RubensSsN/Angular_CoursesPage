package com.rubens.crudspring.controller;

import com.rubens.crudspring.model.Course;
import com.rubens.crudspring.repository.CoursesRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/{id}")
  public ResponseEntity<Course> buscaId(@PathVariable Long id) {
    return coursesRepository.findById(id)
      .map(record -> ResponseEntity.ok().body(record)) //Se nosso optional trouxer uma informaÇão do banco de dados iremos retornar isso no corpo da requisição conforme pedido.
      .orElse(ResponseEntity.notFound().build()); // Se não encontrar iremos fazer o retorno de 404 dizendo que não foi encontrado o registro.
  }

  @PostMapping
  public ResponseEntity<Course> salvar(@RequestBody Course curso) { //Método para salvar dados no banco de dados do tipo Course e que retornará o HTTP 201 (Created) por conta do ResponseEntity.
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(coursesRepository.save(curso));
  }

  //Uma maneira de fazermos resumidamente o ResponseEntity, porém a diferença é que não poderemos alterar nem manusear os dados da reposta.
  /*@PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Course salvar(@RequestBody Course curso) {
    return coursesRepository.save(curso);
  }*/

}
