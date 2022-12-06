package com.rubens.crudspring.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Entity
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 60, nullable = false)
  private String name;

  @Column(length = 12, nullable = false)
  private String category;


}
