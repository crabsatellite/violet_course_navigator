package com.alex.violet_course_navigator.controller;

import com.alex.violet_course_navigator.model.Course;
import com.alex.violet_course_navigator.model.User;
import com.alex.violet_course_navigator.service.CourseService;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping(value = "/courses")
  public List<Course> listCourses(Principal principal) {
    return courseService.listByUser(principal.getName());
  }

  @GetMapping(value = "/courses/{courseId}")
  public Course getCourse(@PathVariable Long courseId, Principal principal) {
    return courseService.findByIdAndInstructor(courseId, principal.getName());
  }

  @PostMapping("/courses")
  public void addCourse(
      @RequestParam("name") String name,
      @RequestParam("address") String address,
      @RequestParam("description") String description,
      @RequestParam("capacity") int capacity,
      @RequestParam("images") MultipartFile[] images,
      Principal principal) {

    Course course =
        new Course.Builder()
            .setName(name)
            .setAddress(address)
            .setDescription(description)
            .setCapacity(capacity)
            .setInstructor(new User.Builder().setUsername(principal.getName()).build())
            .build();
    courseService.add(course, images);
  }

  @DeleteMapping("/courses/{courseId}")
  public void deleteCourse(@PathVariable Long courseId, Principal principal) {
    courseService.delete(courseId, principal.getName());
  }
}
