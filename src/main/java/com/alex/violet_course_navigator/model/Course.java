package com.alex.violet_course_navigator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "course")
@JsonDeserialize(builder = Course.Builder.class)
public class Course implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private String description;
  private String address;

  @JsonProperty("capacity")
  private int capacity;

  @ManyToOne
  @JoinColumn(name = "instructor")
  private User instructor;

  @JsonIgnore
  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<CourseReservedStudent> reservedStudents;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<CourseImage> images;

  public Course() {}

  private Course(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.description = builder.description;
    this.address = builder.address;
    this.capacity = builder.capacity;
    this.instructor = builder.instructor;
    this.reservedStudents = builder.reservedStudents;
  }

  public List<CourseImage> getImages() {
    return images;
  }

  public Course setImages(List<CourseImage> images) {
    this.images = images;
    return this;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getAddress() {
    return address;
  }

  public int getCapacity() {
    return capacity;
  }

  public User getInstructor() {
    return instructor;
  }

  public List<CourseReservedStudent> getReservedStudents() {
    return reservedStudents;
  }

  public static class Builder {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("address")
    private String address;

    @JsonProperty("capacity")
    private int capacity;

    @JsonProperty("instructor")
    private User instructor;

    @JsonProperty("reservedStudents")
    private List<CourseReservedStudent> reservedStudents;

    @JsonProperty("images")
    private List<CourseImage> images;

    public Builder setId(Long id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setAddress(String address) {
      this.address = address;
      return this;
    }

    public Builder setCapacity(int capacity) {
      this.capacity = capacity;
      return this;
    }

    public Builder setInstructor(User instructor) {
      this.instructor = instructor;
      return this;
    }

    public Builder setReservedStudents(List<CourseReservedStudent> reservedStudents) {
      this.reservedStudents = reservedStudents;
      return this;
    }

    public Builder setImages(List<CourseImage> images) {
      this.images = images;
      return this;
    }

    public Course build() {
      return new Course(this);
    }
  }
}
