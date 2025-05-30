package ru.hpclab.hl.module1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequest {
    private Long studentId;
    private Long courseId;
}
