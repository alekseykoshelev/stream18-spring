package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RequestMapping("/student")
@RestController
public class StudentController {
    private final static Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public StudentDTO add(@RequestBody StudentDTO student) {
        return service.add(student);
    }

    @GetMapping
    public Student get(@RequestParam long id) {
        return service.get(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return service.update(student);
    }

    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return service.delete(id);
    }

    @GetMapping("/byAgeBetween")
    public Collection<Student> byAge(@RequestParam int minAge, @RequestParam int maxAge) {
        return service.findByAge(minAge, maxAge);
    }

    @GetMapping("/by-faculty")
    public Collection<Student> findStudentByFaculty(@RequestParam long facId) {
        return service.findStudentsByFaculty(facId);
    }
}
