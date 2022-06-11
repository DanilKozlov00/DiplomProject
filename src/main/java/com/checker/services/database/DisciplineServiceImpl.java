package com.checker.services.database;

import com.checker.model.Discipline;
import com.checker.repository.DisciplineRepository;
import com.checker.services.database.interfaces.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository disciplineRepository;

    @Autowired
    public DisciplineServiceImpl(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Override
    public List<Discipline> findAll() {
        return disciplineRepository.findAll();
    }

    @Override
    public Discipline save(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    @Override
    public Discipline getByName(String name) {
        return disciplineRepository.getDisciplineByName(name);
    }

    @Override
    public void updateDisciplineTaskCountWithName(String name, int taskCount) {
        disciplineRepository.updateDisciplineTaskCountWithName(name, taskCount);
    }
}
