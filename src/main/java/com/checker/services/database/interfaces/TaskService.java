package com.checker.services.database.interfaces;

import com.checker.model.Task;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    Task getById(Integer id);

    Task save(Task task);

    void updateTaskGradeWithTaskId(Boolean grade, Integer id);
}
