package com.rubens.crudspring.enums;

public enum Category {

  BACKEND("Back-end"), FRONTEND("Front-end");

  private String value;

  private Category(String value) {
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
