package com.rubens.crudspring.enums.converters;

import com.rubens.crudspring.enums.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {


  @Override
  public String convertToDatabaseColumn(Category category) { // Pega o Enum e converte em uma String com o mesmo valor (texto).
    if (category == null) {
      return null;
    }
    return category.getValue();
  }

  @Override
  public Category convertToEntityAttribute(String value) {
    if (value == null) {
      return null;
    }
    return Stream.of(Category.values())  // A classe utilitária Stream do Java tem o operador of que consegue transformar qualquer lista de informações (no caso temos aqui um Array de informações) em um streaming.
      .filter(category -> category.getValue().equals(value)) // Com o Straming em mãos conseguimos usar esse operador (filter) que nele conseguimos colocar um filtro que iremos usar que no caso estamos pegando a category do enumerador e usando o equals para poder comparar como value
      .findFirst() // Ao fazermos o filter pode ser que retorne muitos valores, não é o nosso caso aqui, mas é assim que o Java funciona para isso temos o findFirst que de todos os valores retornados pega somente o primeiro.
      .orElseThrow(IllegalArgumentException::new); // Caso não encontre o valor acima a gente vai lançar um IllegalArgumentException que é uma exceção em tempo de execução que irá falar que o argumento não é valido.
  }
}
