package com.alex.violet_course_navigator.service;

import com.alex.violet_course_navigator.exception.UserAlreadyExistException;
import com.alex.violet_course_navigator.model.Authority;
import com.alex.violet_course_navigator.model.User;
import com.alex.violet_course_navigator.model.UserRole;
import com.alex.violet_course_navigator.repository.AuthorityRepository;
import com.alex.violet_course_navigator.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
  private final UserRepository userRepository;
  private final AuthorityRepository authorityRepository;
  private final PasswordEncoder passwordEncoder;

  public RegisterService(
      UserRepository userRepository,
      AuthorityRepository authorityRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.authorityRepository = authorityRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public void add(User user, UserRole role) {
    if (userRepository.existsById(user.getUsername())) {
      throw new UserAlreadyExistException("User already exists");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setEnabled(true);
    userRepository.save(user);
    authorityRepository.save(new Authority(user.getUsername(), role.name()));
  }
}
