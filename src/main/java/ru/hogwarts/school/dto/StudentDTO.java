package ru.hogwarts.school.dto;

public class StudentDTO {

    private Long id;
    private String name;
    private int age;
    private long facultyId;

    public StudentDTO(Long id, String name, int age, long facultyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.facultyId = facultyId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getFacultyId() {
        return facultyId;
    }
}
