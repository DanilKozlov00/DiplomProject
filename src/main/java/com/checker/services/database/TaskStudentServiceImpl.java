package com.checker.services.database;

import com.checker.model.Student;
import com.checker.model.Task;
import com.checker.model.TaskStudent;
import com.checker.repository.TaskStudentRepository;
import com.checker.services.database.interfaces.TaskStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskStudentServiceImpl implements TaskStudentService {

    private final TaskStudentRepository taskStudentRepository;

    @Autowired
    public TaskStudentServiceImpl(TaskStudentRepository taskStudentRepository) {
        this.taskStudentRepository = taskStudentRepository;
    }

    @Override
    public List<TaskStudent> getTasksStudentByStudentId(Long studentId) {
        return taskStudentRepository.getTasksStudentByStudentId(studentId);
    }

    @Override
    public TaskStudent save(TaskStudent taskStudent) {
        return taskStudentRepository.save(taskStudent);
    }
}
