package com.rubens.crudspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

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

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "course_id", nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Estamos apenas lendo a informação do Course.
  private Course course;

}
