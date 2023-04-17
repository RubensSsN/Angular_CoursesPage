package com.rubens.crudspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long id;

  @Column(length = 100, nullable = false)
  public String name;

  @Column(length = 11, nullable = false)
  private String youtubeUrl;

}
