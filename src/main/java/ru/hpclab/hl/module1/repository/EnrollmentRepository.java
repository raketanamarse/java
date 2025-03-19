package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hpclab.hl.module1.model.Course;
import ru.hpclab.hl.module1.model.Enrollment;
import ru.hpclab.hl.module1.model.Student;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByCourse(Course course);
    List<Enrollment> findByStudent(Student student);
}
