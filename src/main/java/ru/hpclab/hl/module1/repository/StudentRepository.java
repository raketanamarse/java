package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hpclab.hl.module1.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}