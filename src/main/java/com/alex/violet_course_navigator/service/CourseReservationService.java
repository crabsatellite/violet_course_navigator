package com.alex.violet_course_navigator.service;

import com.alex.violet_course_navigator.exception.CourseAlreadyFullException;
import com.alex.violet_course_navigator.exception.ReservationCollisionException;
import com.alex.violet_course_navigator.exception.ReservationNotFoundException;
import com.alex.violet_course_navigator.model.*;
import com.alex.violet_course_navigator.repository.CourseRepository;
import com.alex.violet_course_navigator.repository.CourseReservationRepository;
import com.alex.violet_course_navigator.repository.CourseReservationStudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseReservationService {
  private CourseRepository courseRepository;
  private CourseReservationRepository courseReservationRepository;
  private CourseReservationStudentRepository courseReservationStudentRepository;

  @Autowired
  public CourseReservationService(
      CourseRepository courseRepository,
      CourseReservationRepository courseReservationRepository,
      CourseReservationStudentRepository courseReservationStudentRepository) {
    this.courseRepository = courseRepository;
    this.courseReservationRepository = courseReservationRepository;
    this.courseReservationStudentRepository = courseReservationStudentRepository;
  }

  public List<CourseReservation> listByStudent(String username) {
    return courseReservationRepository.findByStudent(
        new User.Builder().setUsername(username).build());
  }

  public List<CourseReservation> listByCourse(Long courseId) {
    return courseReservationRepository.findByCourse(new Course.Builder().setId(courseId).build());
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void add(CourseReservation courseReservation) throws ReservationCollisionException {
    // check if the student has already reserved the course
    List<CourseReservation> list =
        courseReservationRepository.findByStudentAndCourse(
            new User.Builder().setUsername(courseReservation.getStudent().getUsername()).build(),
            new Course.Builder().setId(courseReservation.getCourse().getId()).build());
    if (list.size() > 0) {
      throw new ReservationCollisionException("Duplicate reservation");
    }
    // check if the course is full
    Course course =
        courseRepository
            .findById(courseReservation.getCourse().getId())
            .orElseThrow(() -> new RuntimeException("Course not found"));
    int currentStudentCount =
        courseReservationRepository
            .findByCourseAndReservationDateAfter(
                courseReservation.getCourse(), courseReservation.getReservationDate())
            .size();
    if (currentStudentCount >= course.getCapacity()) {
      throw new CourseAlreadyFullException("Course is already full");
    }

    courseReservationRepository.save(courseReservation);
    courseReservationStudentRepository.save(
        new CourseReservedStudent(
            new CourseReservedStudentKey(
                courseReservation.getCourse().getId(), courseReservation.getStudent()),
            courseReservation.getCourse()));
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void delete(Long reservationId, String username) {
    CourseReservation courseReservation =
        courseReservationRepository.findByIdAndStudent(
            reservationId, new User.Builder().setUsername(username).build());
    if (courseReservation == null) {
      throw new ReservationNotFoundException("Reservation is not available");
    }
    courseReservationStudentRepository.deleteById(
        new CourseReservedStudentKey(
            courseReservation.getCourse().getId(), courseReservation.getStudent()));
    courseReservationRepository.deleteById(reservationId);
  }
}
