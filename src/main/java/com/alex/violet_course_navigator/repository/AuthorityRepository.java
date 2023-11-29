package com.alex.violet_course_navigator.repository;

import com.alex.violet_course_navigator.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {


}
