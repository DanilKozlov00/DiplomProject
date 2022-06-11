package com.checker.services.database;

import com.checker.model.Student;
import com.checker.repository.StudentRepository;
import com.checker.services.database.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.getById(id);
    }

    @Override
    public Student getByNickname(String nickname) {
        return studentRepository.getByNickname(nickname);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void updateStudentWithNickname(String nickname, String password) {
        studentRepository.updateStudentWithNickname(nickname, password);
    }
}
