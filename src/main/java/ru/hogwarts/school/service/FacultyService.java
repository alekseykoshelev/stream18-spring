package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final static Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty add(Faculty faculty) {
        return repository.save(faculty);
    }

    public Faculty get(long id) {
        return repository.findById(id).orElse(null);
    }

    public Faculty update(Faculty faculty) {
        return repository.findById(faculty.getId())
                .map(dbEntity -> {
                    dbEntity.setName(faculty.getName());
                    dbEntity.setColor(faculty.getColor());
                    repository.save(dbEntity);
                    return dbEntity;
                })
                .orElse(null);
    }

    public boolean delete(long id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    public Collection<Faculty> findByColor(String name, String color) {
        return repository.findAllByColorOrNameIgnoreCase(name, color);
    }
}
