package com.alex.violet_course_navigator.exception;

public class CourseAlreadyFullException extends RuntimeException {
    public CourseAlreadyFullException(String message) {
        super(message);
    }
}
