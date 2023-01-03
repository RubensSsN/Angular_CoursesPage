package com.rubens.crudspring.controller;

import com.rubens.crudspring.model.Course;
import com.rubens.crudspring.repository.CoursesRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
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
  public ResponseEntity<Course> buscaId(@PathVariable @NotNull @Positive Long id) {
    return coursesRepository.findById(id)
      .map(recordFound -> ResponseEntity.ok().body(recordFound)) //Se nosso optional trouxer uma informaÇão do banco de dados iremos retornar isso no corpo da requisição conforme pedido.
      .orElse(ResponseEntity.notFound().build()); // Se não encontrar iremos fazer o retorno de 404 dizendo que não foi encontrado o registro.
  }

  @PostMapping
  public ResponseEntity<Course> salvar(@RequestBody @Valid Course curso) { //Método para salvar dados no banco de dados do tipo Course e que retornará o HTTP 201 (Created) por conta do ResponseEntity.
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(coursesRepository.save(curso));
  }

  //Uma maneira de fazermos resumidamente o ResponseEntity, porém a diferença é que não poderemos alterar nem manusear os dados da reposta.
  /*@PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Course salvar(@RequestBody Course curso) {
    return coursesRepository.save(curso);
  }*/

  @PutMapping("/{id}")
  public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course curso) {
    return coursesRepository.findById(id) // Está verificando se o curso existe buscando por id.
      .map(recordFound -> {  // Se o curso existir ele pega o curso faz o map e seta o nome do curso com o curso atualizado e a categoria também.
        recordFound.setName(curso.getName());
        recordFound.setCategory(curso.getCategory());
        Course updated = coursesRepository.save(recordFound); // Variável criada para conter um objeto do tipo Course que irá salvar a informação do curso atualizado.
        return ResponseEntity.ok().body(updated);  //Retorna para o código ok e no corpo o curso já atualizado.
      })
      .orElse(ResponseEntity.notFound().build()); // Se não encontrar iremos fazer o retorno de 404 dizendo que não foi encontrado o registro.
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive  Long id) {
    return coursesRepository.findById(id)// Está verificando se o curso existe buscando por id.
      .map(recordFound -> { // Se o curso existir ele pega o curso faz o map e exclui o curso que tem o id passado na url.
        coursesRepository.deleteById(recordFound.getId());
        return ResponseEntity.noContent().<Void>build(); // Retorna o noContent() que é o nada no corpo da requisição.
      })
      .orElse(ResponseEntity.notFound().build());  // Se não encontrar iremos fazer o retorno de 404 dizendo que não foi encontrado o registro.
  }

}
