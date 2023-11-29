package com.alex.violet_course_navigator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "course_image")
public class CourseImage implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id private String url;

  @ManyToOne
  @JoinColumn(name = "course_id")
  @JsonIgnore
  private Course course;

  public CourseImage() {}

  public CourseImage(String url, Course course) {
    this.url = url;
    this.course = course;
  }

  public String getUrl() {
    return url;
  }

  public CourseImage setUrl(String url) {
    this.url = url;
    return this;
  }

  public Course getCourse() {
    return course;
  }

  public CourseImage setCourse(Course course) {
    this.course = course;
    return this;
  }
}
