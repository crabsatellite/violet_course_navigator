package com.alex.violet_course_navigator.exception;

public class CourseNotExistException extends RuntimeException {
    public CourseNotExistException(String message) {
        super(message);
    }
}
