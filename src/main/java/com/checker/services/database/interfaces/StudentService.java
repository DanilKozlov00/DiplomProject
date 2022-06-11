package com.checker.services.database.interfaces;

import com.checker.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    Student getById(Long id);

    Student getByNickname(String nickname);

    List<Student> findAll();

    Student save(Student student);

    void updateStudentWithNickname(String nickname, String password);
}
