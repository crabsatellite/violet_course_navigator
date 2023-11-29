package com.alex.violet_course_navigator.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "course_reserved_students")
public class CourseReservedStudent implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId private CourseReservedStudentKey id;

  @MapsId("course_id")
  @ManyToOne
  private Course course;

  public CourseReservedStudent() {}

  public CourseReservedStudent(CourseReservedStudentKey id, Course course) {
    this.id = id;
    this.course = course;
  }

  public CourseReservedStudentKey getId() {
    return id;
  }

  public Course getCourse() {
    return course;
  }
}
