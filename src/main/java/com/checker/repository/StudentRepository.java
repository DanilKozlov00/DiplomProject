package com.checker.repository;

import com.checker.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getByNickname(String nickname);

    @Modifying
    @Query("update Student set password = ?2 where nickname = ?1")
    void updateStudentWithNickname(String nickname, String password);
}