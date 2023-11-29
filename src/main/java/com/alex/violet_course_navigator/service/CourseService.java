package com.alex.violet_course_navigator.service;

import com.alex.violet_course_navigator.exception.CourseNotExistException;
import com.alex.violet_course_navigator.model.*;
import com.alex.violet_course_navigator.repository.CourseRepository;
import com.alex.violet_course_navigator.repository.CourseSearchableRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CourseService {

  private final ImageStorageService imageStorageService;
  private final CourseRepository courseRepository;
  private final CourseSearchableRepository courseSearchableRepository;

  public CourseService(
      ImageStorageService imageStorageService,
      CourseRepository courseRepository,
      CourseSearchableRepository courseSearchableRepository) {
    this.imageStorageService = imageStorageService;
    this.courseRepository = courseRepository;
    this.courseSearchableRepository = courseSearchableRepository;
  }

  public List<Course> listByUser(String username) {
    return courseRepository.findByInstructor(new User.Builder().setUsername(username).build());
  }

  public Course findByIdAndInstructor(Long stayId, String username) throws CourseNotExistException {
    Course course =
        courseRepository.findByIdAndInstructor(
            stayId, new User.Builder().setUsername(username).build());
    if (course == null) {
      throw new CourseNotExistException("Course doesn't exist");
    }
    return course;
  }

  public void updateCourseSearchableIndex(Long courseId) {
    Course course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    CourseSearchable courseSearchable = new CourseSearchable(course);
    courseSearchableRepository.save(courseSearchable);
  }

  public void deleteCourseSearchableIndex(Long courseId) {
    courseSearchableRepository.deleteById(courseId);
  }

  @Transactional
  public void add(Course course, MultipartFile[] images) {
    List<String> mediaLinks =
        Arrays.stream(images)
            .parallel()
            .map(image -> imageStorageService.save(image))
            .collect(Collectors.toList());
    List<CourseImage> courseImages = new ArrayList<>();
    for (String mediaLink : mediaLinks) {
      courseImages.add(new CourseImage(mediaLink, course));
    }
    course.setImages(courseImages);

    updateCourseSearchableIndex(courseRepository.save(course).getId());
    courseRepository.save(course);
  }

  @Transactional
  public void delete(Long stayId, String username) throws CourseNotExistException {
    Course course =
        courseRepository.findByIdAndInstructor(
            stayId, new User.Builder().setUsername(username).build());
    if (course == null) {
      throw new CourseNotExistException("Course doesn't exist");
    }
    deleteCourseSearchableIndex(course.getId());
    courseRepository.deleteById(stayId);
  }
}
