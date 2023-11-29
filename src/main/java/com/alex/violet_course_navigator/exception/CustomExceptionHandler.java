package com.alex.violet_course_navigator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(UserAlreadyExistException.class)
  public final ResponseEntity<String> handleUserAlreadyExistExceptions(
      Exception ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UserNotExistException.class)
  public final ResponseEntity<String> handleUserNotExistExceptions(
      Exception ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AwsS3UploadException.class)
  public final ResponseEntity<String> handleGCSUploadExceptions(Exception ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CourseAlreadyFullException.class)
  public final ResponseEntity<String> handleCourseAlreadyFullExceptions(
      Exception ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(CourseNotExistException.class)
  public final ResponseEntity<String> handleCourseNotExistExceptions(
      Exception ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }
}
