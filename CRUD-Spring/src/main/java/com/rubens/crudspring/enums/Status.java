package com.rubens.crudspring.enums;

public enum Status {

  ATIVO("Ativo"), INATIVO("Inativo");

  private String value;

  private Status(String value) {
    this.value = value;
  }

  // Serve para nós conseguirmos obter a categoria sem ser um Enum e sim uma String.
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

}
