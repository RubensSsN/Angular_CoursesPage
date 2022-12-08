package com.rubens.crudspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Entity
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonProperty("_id") //Quando o jxon tiver fazendo a transformação de JSON para OBJETO ou OBJETO para JSON vai transformar o id em _id
  private Long id;

  @Column(length = 60, nullable = false)
  private String name;

  @Column(length = 12, nullable = false)
  private String category;


}
