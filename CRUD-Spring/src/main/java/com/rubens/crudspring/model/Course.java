package com.rubens.crudspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubens.crudspring.enums.Category;
import com.rubens.crudspring.enums.Status;
import com.rubens.crudspring.enums.converters.CategoryConverter;
import com.rubens.crudspring.enums.converters.StatusConverter;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;


@Entity
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id = ?") // Aqui podemos passar o comando SQL que o hibernate irá executar toda vez que chamarmos o método DELETE do nosso repository.
@Where(clause = "status = 'Ativo'") //  toda vez que formos fazer um SELECT no nosso banco de dados o Hibernate automaticamente vai adicionar esse filtro na clausula WHERE.
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonProperty("_id") //Quando o jxon tiver fazendo a transformação de JSON para OBJETO ou OBJETO para JSON vai transformar o id em _id
  private Long id;

  @NotBlank
  @NotNull
  @Length(min = 5, max = 100)
  @Column(length = 100, nullable = false)
  private String name;

  @NotNull
  @Column(length = 10, nullable = false)
  @Convert(converter = CategoryConverter.class)  // Informa qual é o conversor desse campo Category
  private Category category;

  @NotNull
  @Column(length = 10, nullable = false)
  @Convert(converter = StatusConverter.class)
  private Status status = Status.ATIVO;

  @NotNull
  @NotEmpty
  @Valid
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
  private List<Lesson> lessons = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(@NotBlank @NotNull @Length(min = 5, max = 100) String name) {
    this.name = name;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(@NotNull Category category) {
    this.category = category;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(@NotNull Status status) {
    this.status = status;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }
}
