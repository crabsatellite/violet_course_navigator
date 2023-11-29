package com.alex.violet_course_navigator.controller;

import com.alex.violet_course_navigator.model.Token;
import com.alex.violet_course_navigator.model.User;
import com.alex.violet_course_navigator.model.UserRole;
import com.alex.violet_course_navigator.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/authenticate/student")
  public Token authenticateGuest(@RequestBody User user) {
    return authenticationService.authenticate(user, UserRole.STUDENT);
  }

  @PostMapping("/authenticate/instructor")
  public Token authenticateHost(@RequestBody User user) {
    return authenticationService.authenticate(user, UserRole.INSTRUCTOR);
  }
}
