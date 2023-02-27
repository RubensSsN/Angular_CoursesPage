package com.rubens.crudspring.service;

import com.rubens.crudspring.DTO.CourseDTO;
import com.rubens.crudspring.DTO.mapper.CourseMapper;
import com.rubens.crudspring.exception.RecordNotFoundException;
import com.rubens.crudspring.repository.CoursesRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class CourseService {

  private final CoursesRepository coursesRepository;
  private final CourseMapper courseMapper;

  public CourseService(CoursesRepository coursesRepository, CourseMapper courseMapper) {
    this.coursesRepository = coursesRepository;
    this.courseMapper = courseMapper;
  }

  @GetMapping //Informa que o método usado será o GET // MESMA COISA DE = @RequestMapping(method = RequestMethod.GET) \\
  public List<CourseDTO> list() {
    return coursesRepository.findAll().stream().map(courseMapper::toDTO).collect(Collectors.toList()); // Está recebendo os dados do findAll e fazendo um stream com as informações para transformar e uma entidade de DTO e colocar na lista depois todos os dados.
  }

  public CourseDTO buscaId(@PathVariable @NotNull @Positive Long id) {
    return coursesRepository.findById(id).map(courseMapper::toDTO)
      .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public CourseDTO salvar(@Valid @NotNull CourseDTO curso) { //Método para salvar dados no banco de dados do tipo Course e que retornará o HTTP 201 (Created) por conta do ResponseEntity.
    return courseMapper.toDTO(coursesRepository.save(courseMapper.toEntity(curso))); // Está transformando a entidade de Course em uma entidade de CourseDTO e salvando no bano de dados com o método transformando o DTO em entidade para salvar no banco de dados.
  }

  public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO curso) {
    return coursesRepository.findById(id) // Está verificando se o curso existe buscando por id.
      .map(recordFound -> {  // Se o curso existir ele pega o curso faz o map e seta o nome do curso com o curso atualizado e a categoria também.
        recordFound.setName(curso.name());
        recordFound.setCategory(curso.category());
        return courseMapper.toDTO(coursesRepository.save(recordFound)); // Está transformando a entidade de Course em uma entidade de CourseDTO e salvando no bano de dados.
      }).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public void delete(@PathVariable @NotNull @Positive  Long id) {
    coursesRepository.delete(coursesRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id))); // Está verificando se o curso existe buscando por id e se existir deleta, se não ele aciona a exceção de Not Found que fizemos.
  }
}
