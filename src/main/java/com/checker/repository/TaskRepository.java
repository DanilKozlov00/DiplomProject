package com.checker.repository;

import com.checker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Modifying
    @Query("update Task set grade = ?1 where id = ?2")
    void updateTaskGradeWithTaskId(Boolean grade, Integer id);
}