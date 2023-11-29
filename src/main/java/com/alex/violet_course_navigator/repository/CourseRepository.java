package com.alex.violet_course_navigator.repository;

import com.alex.violet_course_navigator.model.Course;
import com.alex.violet_course_navigator.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  List<Course> findByInstructor(User user);

  Course findByIdAndInstructor(Long id, User host);

  List<Course> findByIdInAndCapacityGreaterThanEqual(List<Long> ids, int guestNumber);
}
