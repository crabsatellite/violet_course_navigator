package com.alex.violet_course_navigator.controller;

import com.alex.violet_course_navigator.model.User;
import com.alex.violet_course_navigator.model.UserRole;
import com.alex.violet_course_navigator.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

  private final RegisterService registerService;

  public RegisterController(RegisterService registerService) {
    this.registerService = registerService;
  }

  @PostMapping("/register/student")
  public void addGuest(@RequestBody User user) {
    registerService.add(user, UserRole.STUDENT);
  }

  @PostMapping("/register/instructor")
  public void addHost(@RequestBody User user) {
    registerService.add(user, UserRole.INSTRUCTOR);
  }
}
