package com.rubens.crudspring.service;

import com.rubens.crudspring.DTO.CourseDTO;
import com.rubens.crudspring.DTO.CoursePageDTO;
import com.rubens.crudspring.DTO.mapper.CourseMapper;
import com.rubens.crudspring.exception.RecordNotFoundException;
import com.rubens.crudspring.model.Course;
import com.rubens.crudspring.model.Lesson;
import com.rubens.crudspring.repository.CoursesRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

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

  public CourseDTO buscaId(@NotNull @Positive Long id) {
    return coursesRepository.findById(id).map(courseMapper::toDTO)
      .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public CoursePageDTO list(@PositiveOrZero int pageNumber, @Positive @Max(20) int pageSize) {
    Page<Course> page = coursesRepository.findAll(PageRequest.of(pageNumber, pageSize));
    List<CourseDTO> courses = page.get().map(courseMapper::toDTO).collect(Collectors.toList());
    return new CoursePageDTO(courses, page.getTotalElements(), page.getTotalPages());
  }

  /*
  public List<CourseDTO> list() {
    return coursesRepository.findAll()
      .stream()
      .map(courseMapper::toDTO)
      .collect(Collectors.toList()); // Está recebendo os dados do findAll e fazendo um stream com as informações para transformar e uma entidade de DTO e colocar na lista depois todos os dados.
  }*/

  public CourseDTO salvar(@Valid @NotNull CourseDTO curso) { //Método para salvar dados no banco de dados do tipo Course e que retornará o HTTP 201 (Created) por conta do ResponseEntity.
    return courseMapper.toDTO(coursesRepository.save(courseMapper.toEntity(curso))); // Está transformando a entidade de Course em uma entidade de CourseDTO e salvando no bano de dados com o método transformando o DTO em entidade para salvar no banco de dados.
  }

  public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO curso) {
    return coursesRepository.findById(id) // Está verificando se o curso existe buscando por id.
      .map(recordFound -> {  // Se o curso existir ele pega o curso faz o map e seta o nome do curso com o curso atualizado e a categoria também.
        recordFound.setName(curso.name());
        recordFound.setCategory(courseMapper.convertCategoryValue(curso.category()));
        Course c = courseMapper.toEntity(curso);
        recordFound.getLessons().clear();
        c.getLessons().forEach(recordFound.getLessons()::add);
        return courseMapper.toDTO(coursesRepository.save(recordFound)); // Está transformando a entidade de Course em uma entidade de CourseDTO e salvando no bano de dados.
      }).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public void delete(@NotNull @Positive  Long id) {
    coursesRepository.delete(coursesRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id))); // Está verificando se o curso existe buscando por id e se existir deleta, se não ele aciona a exceção de Not Found que fizemos.
  }
}
