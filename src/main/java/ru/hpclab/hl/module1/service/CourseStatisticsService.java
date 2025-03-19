package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Enrollment;
import ru.hpclab.hl.module1.repository.EnrollmentRepository;

import java.util.stream.Collectors;
import java.util.*;

@Service
public class CourseStatisticsService
{
    private final EnrollmentRepository enrollmentRepository;

    public CourseStatisticsService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public double calculateAverageStudentsPerCourse() {
        // Реализация подсчета среднего количества студентов
        return enrollmentRepository.findAll().stream()
                .collect(Collectors.groupingBy(Enrollment::getCourse))
                .values()
                .stream()
                .mapToInt(List::size)
                .average()
                .orElse(0.0);
    }
}