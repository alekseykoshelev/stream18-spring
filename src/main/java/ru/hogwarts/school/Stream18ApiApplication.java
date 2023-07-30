package ru.hogwarts.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.hogwarts.school.repository.FacultyRepository;

@SpringBootApplication
public class Stream18ApiApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(Stream18ApiApplication.class, args);
    }

}
