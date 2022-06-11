package com.checker.repository;

import com.checker.model.TaskStudent;
import com.checker.model.TaskStudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskStudentRepository extends JpaRepository<TaskStudent, TaskStudentId> {
    List<TaskStudent> getTasksStudentByStudentId(Long studentId);
}