package com.rubens.crudspring.service;

import com.rubens.crudspring.exception.RecordNotFoundException;
import com.rubens.crudspring.model.Course;
import com.rubens.crudspring.repository.CoursesRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CourseService {

  private final CoursesRepository coursesRepository;

  public CourseService(CoursesRepository coursesRepository) {
    this.coursesRepository = coursesRepository;
  }

  @GetMapping //Informa que o método usado será o GET // MESMA COISA DE = @RequestMapping(method = RequestMethod.GET) \\
  public List<Course> list() {
    return coursesRepository.findAll();
  }

  public Course buscaId(@PathVariable @NotNull @Positive Long id) {
    return coursesRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public Course salvar(@Valid Course curso) { //Método para salvar dados no banco de dados do tipo Course e que retornará o HTTP 201 (Created) por conta do ResponseEntity.
    return coursesRepository.save(curso);
  }

  public Course update(@NotNull @Positive Long id, @Valid Course curso) {
    return coursesRepository.findById(id) // Está verificando se o curso existe buscando por id.
      .map(recordFound -> {  // Se o curso existir ele pega o curso faz o map e seta o nome do curso com o curso atualizado e a categoria também.
        recordFound.setName(curso.getName());
        recordFound.setCategory(curso.getCategory());
        return coursesRepository.save(recordFound); // Variável criada para conter um objeto do tipo Course que irá salvar a informação do curso atualizado.
      }).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public void delete(@PathVariable @NotNull @Positive  Long id) {
    coursesRepository.delete(coursesRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id))); // Está verificando se o curso existe buscando por id e se existir deleta, se não ele aciona a exceção de Not Found que fizemos.
  }
}
