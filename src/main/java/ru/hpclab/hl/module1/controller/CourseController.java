package ru.hpclab.hl.module1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Course;
import ru.hpclab.hl.module1.repository.CourseRepository;
import ru.hpclab.hl.module1.service.ObservabilityService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseRepository courseRepository;
    private final ObservabilityService observabilityService;

    @GetMapping
    public List<Course> getAllCourses() {
        String operation = "getAllCourses";
        UUID timerId = observabilityService.startTimer(operation);

        List<Course> courses = courseRepository.findAll();

        observabilityService.stopTimer(timerId);
        return courses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        String operation = "getCourse";
        UUID timerId = observabilityService.startTimer(operation);

        ResponseEntity<Course> response = courseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        observabilityService.stopTimer(timerId);
        return response;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        String operation = "createCourse";
        UUID timerId = observabilityService.startTimer(operation);

        ResponseEntity<Course> response = ResponseEntity.ok(courseRepository.save(course));

        observabilityService.stopTimer(timerId);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        String operation = "deleteCourse";
        UUID timerId = observabilityService.startTimer(operation);

        ResponseEntity<Void> response;
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            response = ResponseEntity.noContent().build();
        } else {
            response = ResponseEntity.notFound().build();
        }

        observabilityService.stopTimer(timerId);
        return response;
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCourses() {
        String operation = "deleteAllCourses";
        UUID timerId = observabilityService.startTimer(operation);

        courseRepository.deleteAll();

        observabilityService.stopTimer(timerId);
        return ResponseEntity.ok().build();
    }
}




//package ru.hpclab.hl.module1.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.hpclab.hl.module1.model.Course;
//import ru.hpclab.hl.module1.repository.CourseRepository;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/courses")
//@RequiredArgsConstructor
//public class CourseController {
//    private final CourseRepository courseRepository;
//
//    @GetMapping
//    public List<Course> getAllCourses() {
//        return courseRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
//        return courseRepository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
//        return ResponseEntity.ok(courseRepository.save(course));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
//        if (courseRepository.existsById(id)) {
//            courseRepository.deleteById(id);
//            return ResponseEntity.noContent().build(); // HTTP 204
//        } else {
//            return ResponseEntity.notFound().build(); // HTTP 404
//        }
//    }
//
//    @DeleteMapping("/all")
//    public ResponseEntity<Void> deleteAllCourses() {
//        courseRepository.deleteAll();
//        return ResponseEntity.ok().build();
//    }
//
//
//}
