package com.alex.violet_course_navigator.repository;

import com.alex.violet_course_navigator.model.CourseSearchable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSearchableRepository extends ElasticsearchRepository<CourseSearchable, Long> {

}

