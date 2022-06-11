package com.checker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "discipline", nullable = false, length = 30)
    private String discipline;

    @Column(name = "grade", nullable = false)
    private Boolean grade;

    @Column(name = "number", nullable = false)
    private Integer number;

    protected Task() {
    }

    public Task(String discipline, int number, Boolean grade) {
        this.discipline = discipline;
        this.grade = grade;
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getGrade() {
        return grade;
    }

    public void setGrade(Boolean grade) {
        this.grade = grade;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}