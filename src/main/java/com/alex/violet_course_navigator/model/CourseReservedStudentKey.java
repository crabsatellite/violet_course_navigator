package com.alex.violet_course_navigator.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class CourseReservedStudentKey implements Serializable {
  private Long course_id;
  private String username;

  public CourseReservedStudentKey() {}

  public CourseReservedStudentKey(Long course_id, User student) {
    this.course_id = course_id;
    this.username = student.getUsername();
  }

  public Long getCourse_id() {
    return course_id;
  }

  public CourseReservedStudentKey setCourse_id(Long course_id) {
    this.course_id = course_id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public CourseReservedStudentKey setUsername(String username) {
    this.username = username;
    return this;
  }

  public CourseReservedStudentKey setStudent(User student) {
    this.username = student.getUsername();
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CourseReservedStudentKey that = (CourseReservedStudentKey) o;
    return course_id.equals(that.course_id) && username.equals(that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(course_id, username);
  }
}
