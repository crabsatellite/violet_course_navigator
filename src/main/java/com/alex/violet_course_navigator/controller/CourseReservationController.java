package com.alex.violet_course_navigator.controller;

import com.alex.violet_course_navigator.model.CourseReservation;
import com.alex.violet_course_navigator.model.User;
import com.alex.violet_course_navigator.service.CourseReservationService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseReservationController {
    private CourseReservationService courseReservationService;

    @Autowired
    public CourseReservationController(CourseReservationService courseReservationService) {
        this.courseReservationService = courseReservationService;
    }

    @GetMapping(value = "/course_reservations")
    public List<CourseReservation> listReservations(Principal principal) {
        return courseReservationService.listByStudent(principal.getName());
    }

    @GetMapping(value = "/course_reservations/{courseId}")
    public List<CourseReservation> listReservationsByCourse(@PathVariable Long courseId, Principal principal) {
        return courseReservationService.listByCourse(courseId);
    }

    @PostMapping("/course_reservations")
    public void addReservation(@RequestBody CourseReservation courseReservation, Principal principal) {
        courseReservation.setStudent(new User.Builder().setUsername(principal.getName()).build());
        courseReservationService.add(courseReservation);
    }

    @DeleteMapping("/course_reservations/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId, Principal principal) {
        courseReservationService.delete(reservationId, principal.getName());
    }
}

