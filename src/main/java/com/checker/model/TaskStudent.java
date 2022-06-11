package com.checker.model;

import javax.persistence.*;

@Entity
@Table(name = "task_student")
public class TaskStudent {
    @EmbeddedId
    private TaskStudentId id;

    @MapsId("idTask")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_task", nullable = false)
    private Task task;

    @MapsId("idStudent")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;

    protected TaskStudent() {
    }

    public TaskStudent(Task task, Student student) {
        this.task = task;
        this.student = student;
        this.id = new TaskStudentId(task.getId(), student.getId());
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskStudentId getId() {
        return id;
    }

    public void setId(TaskStudentId id) {
        this.id = id;
    }
}