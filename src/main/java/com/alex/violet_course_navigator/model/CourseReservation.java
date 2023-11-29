package com.alex.violet_course_navigator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "course_reservation")
@JsonDeserialize(builder = CourseReservation.Builder.class)
public class CourseReservation implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User student;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @JsonProperty("reservation_date")
  private LocalDate reservationDate = LocalDate.now();

  public CourseReservation() {}

  private CourseReservation(Builder builder) {
    this.id = builder.id;
    this.student = builder.student;
    this.course = builder.course;
  }

  public Long getId() {
    return id;
  }

  public LocalDate getReservationDate() {
    return reservationDate;
  }

  public User getStudent() {
    return student;
  }

  public CourseReservation setStudent(User guest) {
    this.student = guest;
    return this;
  }

  public Course getCourse() {
    return course;
  }

  public static class Builder {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("reservation_date")
    private LocalDate reservationDate;

    @JsonProperty("student")
    private User student;

    @JsonProperty("course")
    private Course course;

    public Builder setId(Long id) {
      this.id = id;
      return this;
    }

    public Builder setReservationDate(LocalDate reservationDate) {
      this.reservationDate = reservationDate;
      return this;
    }

    public Builder setGuest(User guest) {
      this.student = guest;
      return this;
    }

    public Builder setCourse(Course course) {
      this.course = course;
      return this;
    }

    public CourseReservation build() {
      return new CourseReservation(this);
    }
  }
}
