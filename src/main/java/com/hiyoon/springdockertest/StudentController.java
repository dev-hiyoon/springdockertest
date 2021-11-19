package com.hiyoon.springdockertest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping(value = "/{id}")
    public Student getName(@PathVariable("id") Integer id) {
        return studentRepository.getById(id);
    }

    @PostMapping
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
}
