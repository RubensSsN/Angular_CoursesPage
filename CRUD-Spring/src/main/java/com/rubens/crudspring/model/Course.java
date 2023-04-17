package com.rubens.crudspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubens.crudspring.enums.Category;
import com.rubens.crudspring.enums.Status;
import com.rubens.crudspring.enums.converters.CategoryConverter;
import com.rubens.crudspring.enums.converters.StatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Data
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

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "course_id")
  private List<Lesson> lessons = new ArrayList<>();

}
