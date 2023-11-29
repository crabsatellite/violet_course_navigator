package com.alex.violet_course_navigator.service;

import com.alex.violet_course_navigator.model.Course;
import com.alex.violet_course_navigator.model.CourseSearchable;
import com.alex.violet_course_navigator.repository.*;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class CourseSearchService {

  private final CourseRepository courseRepository;
  private final ElasticsearchOperations elasticsearchOperations;
  private final CourseReservationStudentRepository courseReservationStudentRepository;

  public CourseSearchService(
      CourseRepository courseRepository,
      ElasticsearchOperations elasticsearchOperations,
      CourseReservationStudentRepository courseReservationStudentRepository) {
    this.courseRepository = courseRepository;
    this.elasticsearchOperations = elasticsearchOperations;
    this.courseReservationStudentRepository = courseReservationStudentRepository;
  }

  public List<Course> search(String keywords) {
    if (keywords == null || keywords.isEmpty()) {
      return courseRepository.findAll();
    }

    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    queryBuilder.withQuery(QueryBuilders.multiMatchQuery(keywords, "name", "description"));

    SearchHits<CourseSearchable> searchResult =
        elasticsearchOperations.search(queryBuilder.build(), CourseSearchable.class);
    List<CourseSearchable> coursesSearchable = new ArrayList<>();
    for (SearchHit<CourseSearchable> hit : searchResult.getSearchHits()) {
      coursesSearchable.add(hit.getContent());
    }
    // turn courseSearchable into course
    List<Course> courses = new ArrayList<>();
    for (CourseSearchable courseSearchable : coursesSearchable) {
      Course course =
          courseRepository
              .findById(courseSearchable.getId())
              .orElseThrow(() -> new RuntimeException("Course not found"));
      courses.add(course);
    }
    return courses;
  }
}
