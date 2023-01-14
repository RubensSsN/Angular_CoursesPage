package com.rubens.crudspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

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
  @Length(max = 12)
  @Pattern(regexp = "Back-end|Front-end")
  @Column(length = 12, nullable = false)
  private String category;

  @NotNull
  @Column(length = 10, nullable = false)
  @Pattern(regexp = "Ativo|Inativo")
  @Length(max = 10)
  private String status = "Ativo";

}
