package com.alex.violet_course_navigator.controller;

import com.alex.violet_course_navigator.model.Course;
import com.alex.violet_course_navigator.service.CourseSearchService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseSearchController {
  private final CourseSearchService courseSearchService;

  public CourseSearchController(CourseSearchService courseSearchService) {
    this.courseSearchService = courseSearchService;
  }

  @GetMapping(value = "/course_search")
  public List<Course> searchStays(@RequestParam(name = "key_words") String keyWords) {

    return courseSearchService.search(keyWords);
  }
}
