package com.hiyoon.springdockertest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping(value = "/ids/{id}")
    public Student getStudentById(@PathVariable("id") Integer id) {
        Student student = studentRepository.findById(id).orElse(new Student());
        log.info("getStudent. student: {}", student);
        return student;
    }

    @GetMapping(value = "/names/{name}")
    public Student getStudentByName(@PathVariable("name") String name) {
        Student student = studentRepository.findByName(name);
        log.info("getStudent. student: {}", student);
        return student;
    }

    @PostMapping
    public Student saveStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
}
