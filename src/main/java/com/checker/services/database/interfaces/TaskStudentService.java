package com.checker.services.database.interfaces;

import com.checker.model.Student;
import com.checker.model.Task;
import com.checker.model.TaskStudent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskStudentService {
    List<TaskStudent> getTasksStudentByStudentId(Long studentId);

    TaskStudent save(TaskStudent taskStudent);
}
