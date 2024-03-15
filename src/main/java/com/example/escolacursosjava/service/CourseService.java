package com.example.escolacursosjava.service;

import com.example.escolacursosjava.exception.RecordNotFoundException;
import com.example.escolacursosjava.model.Course;
import com.example.escolacursosjava.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
@Validated
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public List<Course> list() {
        return courseRepository.findAll();
    }
    public Course findById(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }
    public Course create(@Valid Course course){
        return courseRepository.save(course);
    }
    public Course update(@NotNull @Positive Long id, @Valid Course course) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    return  courseRepository.save(recordFound);
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }
    public boolean delete(@NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(record -> {
                    courseRepository.deleteById(id);
                    return true;
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

}
