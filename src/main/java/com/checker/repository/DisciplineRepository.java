package com.checker.repository;

import com.checker.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {
    Discipline getDisciplineByName(String name);

    @Modifying
    @Query("update Discipline set tasksCount = ?2 where name = ?1")
    void updateDisciplineTaskCountWithName(String name, int taskCount);
}
