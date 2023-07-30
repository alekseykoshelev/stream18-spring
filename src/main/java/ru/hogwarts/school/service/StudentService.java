package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository repository, FacultyRepository facultyRepository) {
        this.repository = repository;
        this.facultyRepository = facultyRepository;
    }

    public StudentDTO add(StudentDTO studentDTO) {
        Student s = new Student();
        s.setName(studentDTO.getName());
        s.setAge(studentDTO.getAge());
        s.setFaculty(facultyRepository.findById(studentDTO.getFacultyId()).orElse(null));
        var saved = repository.save(s);
        return new StudentDTO(saved.getId(), saved.getName(), saved.getAge(), studentDTO.getFacultyId());
    }

    public Student get(long id) {
        return repository.findById(id).orElse(null);
    }

    public Student update(Student student) {
        return repository.findById(student.getId())
                .map(dbEntity -> {
                    dbEntity.setName(student.getName());
                    dbEntity.setAge(student.getAge());
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

    public Collection<Student> findByAge(int minAge, int maxAge) {
        return repository.findAllByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> findStudentsByFaculty(long facultyId) {
        return repository.findAllByFacultyId(facultyId);
    }
}
