package com.checker.services.database;

import com.checker.model.Task;
import com.checker.repository.TaskRepository;
import com.checker.services.database.interfaces.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getById(Integer id) {
        return taskRepository.getById(id);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void updateTaskGradeWithTaskId(Boolean grade, Integer id) {
        taskRepository.updateTaskGradeWithTaskId(grade, id);
    }

}
