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

    public StudentDTO() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFacultyId(long facultyId) {
        this.facultyId = facultyId;
    }
}
