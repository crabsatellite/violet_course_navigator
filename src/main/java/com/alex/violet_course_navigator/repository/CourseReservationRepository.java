package com.alex.violet_course_navigator.repository;

import com.alex.violet_course_navigator.model.Course;
import com.alex.violet_course_navigator.model.CourseReservation;
import com.alex.violet_course_navigator.model.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReservationRepository extends JpaRepository<CourseReservation, Long> {
  List<CourseReservation> findByStudent(User student);

  List<CourseReservation> findByCourse(Course course);

  CourseReservation findByIdAndStudent(Long id, User student);

  List<CourseReservation> findByStudentAndCourse(User student, Course course);

  List<CourseReservation> findByCourseAndReservationDateAfter(Course course, LocalDate date);
}
