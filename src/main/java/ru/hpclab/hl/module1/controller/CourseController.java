package ru.hpclab.hl.module1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Course;
import ru.hpclab.hl.module1.repository.CourseRepository;
import ru.hpclab.hl.module1.service.ObservabilityService;
import java.util.UUID;

import java.util.List;






@RequiredArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final ObservabilityService observabilityService;

    @GetMapping
    public List<Course> getAllCourses() {
        String operation = "getAllCourses";
        UUID timerId = observabilityService.startTimer(operation); // старт

        List<Course> result = courseRepository.findAll();

        observabilityService.stopTimer(timerId); // стоп
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        String operation = "getCourse_id";
        UUID timerId = observabilityService.startTimer(operation); // старт

        ResponseEntity<Course> response = courseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        observabilityService.stopTimer(timerId); // стоп
        return response;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        String operation = "createCourse";
        UUID timerId = observabilityService.startTimer(operation); // старт

        Course savedCourse = courseRepository.save(course);

        observabilityService.stopTimer(timerId); // стоп
        return ResponseEntity.ok(savedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        String operation = "deleteCourse_id";
        UUID timerId = observabilityService.startTimer(operation); // старт

        ResponseEntity<Void> response;
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            response = ResponseEntity.noContent().build(); // HTTP 204
        } else {
            response = ResponseEntity.notFound().build(); // HTTP 404
        }

        observabilityService.stopTimer(timerId); // стоп
        return response;
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCourses() {
        String operation = "deleteAllCourses";
        UUID timerId = observabilityService.startTimer(operation); // старт

        courseRepository.deleteAll();

        observabilityService.stopTimer(timerId); // стоп
        return ResponseEntity.ok().build();
    }
}


//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/courses")
//public class CourseController {
//    private final CourseRepository courseRepository;
//    private final ObservabilityService observabilityService;
//
////    @GetMapping
////    public List<Course> getAllCourses() {
////        return courseRepository.findAll();
////    }
//    @GetMapping
//    public List<Course> getAllCourses() {
//        String operation = "getAllCourses";
//        UUID timerId = observabilityService.startTimer(operation); // старт
//
//        List<Course> result = courseRepository.findAll();
//
//        observabilityService.stopTimer(timerId); // стоп
//        return result;
//    }
//
//
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
