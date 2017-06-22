package com.myshop.repository;

public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  private Long id;

  public NotFoundException(Long id) {
    this.id = id;
  }
  
  public Long getId() {
    return id;
  }
  
}
