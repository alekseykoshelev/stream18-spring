package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final String avatarDir;
    private final boolean saveOnDisk;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository,
                         @Value("${avatars.dir}") String avatarDir,
                         @Value("${save-avatars-on-disk}") boolean saveOnDisk) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarDir = avatarDir;
        this.saveOnDisk = saveOnDisk;
    }

    public void upload(long studentId, MultipartFile file) throws IOException {
        var student = studentRepository
                .findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        var filePath = saveOnDisk ? saveOnDisk(file, student) : null;
        var avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setFilePath(filePath);
        avatar.setData(file.getBytes());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setStudent(student);
        avatarRepository.save(avatar);
    }

    private String saveOnDisk(MultipartFile file, Student student) throws IOException {
        var path = Path.of(avatarDir);
        if (!path.toFile().exists()) {
            Files.createDirectories(path);
        }

        var dotIndex = file.getOriginalFilename().lastIndexOf('.');
        var ext = file.getOriginalFilename().substring(dotIndex + 1);
        var filePath = avatarDir + "/" + student.getId() + "_" + student.getName() + "." + ext;

        try (var in = file.getInputStream();
             var out = new FileOutputStream(filePath)) {
            in.transferTo(out);
        }
        return filePath;
    }

    public Avatar findAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }
}
