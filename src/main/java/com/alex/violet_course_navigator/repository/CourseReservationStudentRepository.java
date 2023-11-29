package com.alex.violet_course_navigator.repository;

import com.alex.violet_course_navigator.model.CourseReservedStudent;
import com.alex.violet_course_navigator.model.CourseReservedStudentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReservationStudentRepository extends JpaRepository<CourseReservedStudent, CourseReservedStudentKey> {


}

