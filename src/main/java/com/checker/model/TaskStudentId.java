package com.checker.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TaskStudentId implements Serializable {
    private static final long serialVersionUID = 8265835109603827422L;
    @Column(name = "id_task", nullable = false)
    private Integer idTask;
    @Column(name = "id_student", nullable = false)
    private Long idStudent;

    protected TaskStudentId() {
    }

    public TaskStudentId(Integer idTask, Long idStudent) {
        this.idStudent = idStudent;
        this.idTask = idTask;
    }

    public Long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, idStudent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TaskStudentId entity = (TaskStudentId) o;
        return Objects.equals(this.idTask, entity.idTask) &&
                Objects.equals(this.idStudent, entity.idStudent);
    }
}