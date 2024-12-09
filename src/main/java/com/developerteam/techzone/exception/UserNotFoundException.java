package com.developerteam.techzone.exception;

public class UserNotFoundException extends BaseException {

  public UserNotFoundException(ErrorMessage errorMessage) {
    super(errorMessage);
  }
}
