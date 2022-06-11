package com.checker.controller;

import com.checker.model.*;
import com.checker.services.database.interfaces.DisciplineService;
import com.checker.services.database.interfaces.StudentService;
import com.checker.services.database.interfaces.TaskService;
import com.checker.services.database.interfaces.TaskStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutController {

    private final StudentService studentService;
    private final TaskService taskService;
    private final TaskStudentService taskStudentService;
    private final DisciplineService disciplineService;

    @Autowired
    public PutController(StudentService studentService, TaskService taskService, TaskStudentService taskStudentService, DisciplineService disciplineService) {
        this.studentService = studentService;
        this.taskService = taskService;
        this.taskStudentService = taskStudentService;
        this.disciplineService = disciplineService;
    }


    @PutMapping(value = "student", params = {"nickname", "password"})
    public ResponseEntity insertStudentEntity(@RequestParam(value = "nickname") String nickname, @RequestParam(value = "password") String password) {
        if (studentService.getByNickname(nickname) == null) {
            Student student = studentService.save(new Student(nickname, password));
            if (student == null) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "discipline", params = {"name", "task_count"})
    public ResponseEntity insertDisciplineEntity(@RequestParam(value = "name") String name, @RequestParam(value = "task_count") Integer taskCount) {
        if (disciplineService.getByName(name) == null) {
            Discipline discipline = disciplineService.save(new Discipline(name, taskCount));
            if (discipline == null) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "task", params = {"nickname", "discipline", "task_number", "grade"})
    public ResponseEntity insertTaskEntity(@RequestParam(value = "nickname") String nickname,
                                           @RequestParam(value = "discipline") String discipline,
                                           @RequestParam(value = "task_number") Integer task_number,
                                           @RequestParam(value = "grade") Boolean grade) {
        Student student;
        if ((student = studentService.getByNickname(nickname)) != null) {
            Discipline disciplineData;
            if ((disciplineData = disciplineService.getByName(discipline)) != null) {
                if (task_number <= disciplineData.getTasksCount() && task_number > 0) {
                    Task task;
                    if ((task = taskService.save(new Task(discipline, task_number, grade))) != null) {
                        if (taskStudentService.save(new TaskStudent(task, student)) != null) {
                            return new ResponseEntity(HttpStatus.OK);
                        }
                    }
                }
            }
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}