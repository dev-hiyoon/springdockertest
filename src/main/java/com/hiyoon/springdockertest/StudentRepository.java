package com.hiyoon.springdockertest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByName(@Param("name") String name);
}
