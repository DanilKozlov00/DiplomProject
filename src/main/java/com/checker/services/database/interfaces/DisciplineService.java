package com.checker.services.database.interfaces;

import com.checker.model.Discipline;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DisciplineService {

    List<Discipline> findAll();

    Discipline save(Discipline discipline);

    Discipline getByName(String name);

    void updateDisciplineTaskCountWithName(String name, int taskCount);
}
