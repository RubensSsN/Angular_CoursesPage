package com.rubens.crudspring.DTO;

import org.springframework.data.domain.Page;

import java.util.List;

public record CoursePageDTO(List<CourseDTO> courses, long totalElements, int totalPages) {



}
