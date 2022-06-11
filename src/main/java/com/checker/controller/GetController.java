package com.checker.controller;

import com.checker.model.Discipline;
import com.checker.model.Student;
import com.checker.model.Task;
import com.checker.model.TaskStudent;
import com.checker.services.TaskCheckService;
import com.checker.services.database.interfaces.DisciplineService;
import com.checker.services.database.interfaces.StudentService;
import com.checker.services.database.interfaces.TaskService;
import com.checker.services.database.interfaces.TaskStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GetController {

    private static final String GRADE = "Оценка: ";
    private static final String COLORS_STRING_PATTERN = "\\[32m|\\[33m|\\[31m|\\[m|\\[0m";
    private static final String NEW_LINE = "\n";

    private final StudentService studentService;
    private final TaskService taskService;
    private final TaskStudentService taskStudentService;
    private final DisciplineService disciplineService;
    private final TaskCheckService taskCheckService;

    @Autowired
    public GetController(StudentService studentService, TaskService taskService, TaskStudentService taskStudentService, DisciplineService disciplineService, TaskCheckService taskCheckService) {
        this.studentService = studentService;
        this.taskService = taskService;
        this.taskStudentService = taskStudentService;
        this.disciplineService = disciplineService;
        this.taskCheckService = taskCheckService;
    }

    @GetMapping(value = "students")
    public List<Student> getStudents() {
        return studentService.findAll();
    }

    @GetMapping(value = "student", params = {"nickname"})
    public Student getStudentWithNickname(@RequestParam(value = "nickname") String nickName) {
        return studentService.getByNickname(nickName);
    }

    @GetMapping(value = "student", params = {"id"})
    public Student getStudentWithId(@RequestParam(value = "id") Long id) {
        return studentService.getById(id);
    }

    @GetMapping(value = "studentTasks", params = {"id"})
    public List<Task> getTasksWithStudentId(@RequestParam(value = "id") Long id) {
        return taskStudentService.getTasksStudentByStudentId(id).stream().map(TaskStudent::getTask).collect(Collectors.toList());
    }

    @GetMapping(value = "task", params = {"id"})
    public Task getTaskWithId(@RequestParam(value = "id") Integer id) {
        return taskService.getById(id);
    }

    @GetMapping(value = "disciplines")
    public List<Discipline> getDisciplines() {
        return disciplineService.findAll();
    }

    @GetMapping(value = "discipline", params = {"name"})
    public Discipline getDisciplineByName(@RequestParam(value = "name") String name) {
        return disciplineService.getByName(name);
    }

    @GetMapping(value = "check", params = {"nickname", "discipline", "task_number", "ip"})
    public String startCheck(@RequestParam(value = "nickname") String nickname,
                             @RequestParam(value = "discipline") String discipline,
                             @RequestParam(value = "task_number") Integer task_number,
                             @RequestParam(value = "ip") String ip) {
        Student student;
        if ((student = studentService.getByNickname(nickname)) != null) {
            List<String> checkResult = taskCheckService.initTestScript(ip, task_number, discipline);
            Boolean grade = taskCheckService.getGradeFromScriptResult(checkResult);
            Discipline disciplineData;
            if ((disciplineData = disciplineService.getByName(discipline)) != null) {
                if (task_number <= disciplineData.getTasksCount() && task_number > 0) {
                    Task task;
                    if ((task = taskService.save(new Task(discipline, task_number, grade))) != null) {
                        if (taskStudentService.save(new TaskStudent(task, student)) != null) {
                            return GRADE + grade + NEW_LINE + TaskCheckService.generateOutputMessage(checkResult).replaceAll(COLORS_STRING_PATTERN, "");
                        }
                    }
                }
            }
        }
        return "Error";
    }
}

