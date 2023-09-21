package com.rubens.crudspring.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubens.crudspring.enums.Category;
import com.rubens.crudspring.enums.validation.ValueOfEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;


public record CourseDTO(@JsonProperty("_id") Long id,
                        @NotNull @NotBlank @Length(min = 5, max = 100) String name,
                        @NotNull @Length(max = 12) @ValueOfEnum(enumClass = Category.class) String category, @NotNull @NotEmpty @Valid List<LessonsDTO> lessons) {
}

// Para validar o Enum a gente usou o ValueOfEnum, mas se quisermos especificar que queremos somente determinados valores da classe do Enum podemos usar o Subset ao invés do Validator String.
// Link do Artigo para auxílio do assunto acima comentado: https://www.baeldung.com/javax-validations-enums
