package com.checker.controller;

import com.checker.model.Student;
import com.checker.model.Task;
import com.checker.model.TaskStudent;
import com.checker.services.database.interfaces.DisciplineService;
import com.checker.services.database.interfaces.StudentService;
import com.checker.services.database.interfaces.TaskService;
import com.checker.services.database.interfaces.TaskStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {

    private final StudentService studentService;
    private final TaskService taskService;
    private final TaskStudentService taskStudentService;
    private final DisciplineService disciplineService;

    @Autowired
    public PostController(StudentService studentService, TaskService taskService, TaskStudentService taskStudentService, DisciplineService disciplineService) {
        this.studentService = studentService;
        this.taskService = taskService;
        this.taskStudentService = taskStudentService;
        this.disciplineService = disciplineService;
    }

    @PostMapping(value = "task", params = {"nickname", "discipline", "task_number", "grade"})
    public ResponseEntity updateTaskEntity(@RequestParam(value = "nickname") String nickname,
                                           @RequestParam(value = "discipline") String discipline,
                                           @RequestParam(value = "task_number") Integer task_number,
                                           @RequestParam(value = "grade") Boolean grade) {
        Student student;
        if ((student = studentService.getByNickname(nickname)) != null) {
            List<Task> tasks = taskStudentService.getTasksStudentByStudentId(student.getId()).stream().map(TaskStudent::getTask).collect(Collectors.toList());
            for (Task task : tasks) {
                if (task.getDiscipline().equals(discipline) && task.getNumber().equals(task_number)) {
                    taskService.updateTaskGradeWithTaskId(grade, task.getId());
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "student", params = {"nickname", "password"})
    public ResponseEntity updateStudentPassword(@RequestParam(value = "nickname") String nickname,
                                                @RequestParam(value = "password") String password
    ) {
        Student student;
        if ((student = studentService.getByNickname(nickname)) != null) {
            studentService.updateStudentWithNickname(student.getNickname(), new Argon2PasswordEncoder().encode(password));
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "discipline", params = {"name", "task_count"})
    public ResponseEntity updateDisciplineTaskCount(@RequestParam(value = "name") String name,
                                                    @RequestParam(value = "task_count") int taskCount
    ) {
        if (disciplineService.getByName(name) != null) {
            disciplineService.updateDisciplineTaskCountWithName(name, taskCount);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
