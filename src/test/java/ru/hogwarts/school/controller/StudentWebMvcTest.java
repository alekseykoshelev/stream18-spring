package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentWebMvcTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }


    @Test
    public void testPostStudent() {
        StudentDTO dto = new StudentDTO();
        dto.setAge(20);
        dto.setName("Ron");
        StudentDTO result = restTemplate.postForObject("http://localhost:" + port + "/student", dto, StudentDTO.class);
        Assertions.assertThat(result.getAge()).isEqualTo(20);
        Assertions.assertThat(result.getName()).isEqualTo("Ron");
        Assertions.assertThat(result.getFacultyId()).isEqualTo(0L);
    }

    @Test
    public void testGetStudents() {
        StudentDTO dto = new StudentDTO();
        dto.setAge(18);
        dto.setName("Harry");
        StudentDTO saved = restTemplate.postForObject("http://localhost:" + port + "/student", dto, StudentDTO.class);
        StudentDTO result = this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + saved.getId(), StudentDTO.class);
        Assertions.assertThat(result.getName()).isEqualTo("Harry");
        Assertions.assertThat(result.getAge()).isEqualTo(18);
    }


    @Test
    public void testPutStudent() {
        StudentDTO dto = new StudentDTO();
        dto.setAge(18);
        dto.setName("Harry");
        StudentDTO saved = restTemplate.postForObject("http://localhost:" + port + "/student", dto, StudentDTO.class);
        saved.setName("Germiona");

        ResponseEntity<Student> studentEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(saved),
                Student.class);

        Assertions.assertThat(studentEntity.getBody().getName()).isEqualTo("Germiona");
        Assertions.assertThat(studentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetAll() {
        ResponseEntity<List<Student>> result = restTemplate.exchange("http://localhost:" + port + "/student/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {});
    }
}
