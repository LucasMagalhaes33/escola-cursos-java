package com.example.escolacursosjava.service;

import com.example.escolacursosjava.dto.CourseDTO;
import com.example.escolacursosjava.dto.mapper.CourseMapper;
import com.example.escolacursosjava.enums.Category;
import com.example.escolacursosjava.exception.RecordNotFoundException;
import com.example.escolacursosjava.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Validated
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }
    public List<CourseDTO> list() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDTO)
                .toList();
    }
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }
    public CourseDTO create(@Valid CourseDTO course){
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }
    public CourseDTO update(@NotNull @Positive Long id, @Valid CourseDTO course) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.name());
                    recordFound.setCategory(Category.FRONT_END);
                    return  courseMapper.toDTO(courseRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }
    public boolean delete(@NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(record -> {
                    courseRepository.deleteById(id);
                    return true;
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

}
