package com.example.escolacursosjava.service;

import com.example.escolacursosjava.dto.CourseDTO;
import com.example.escolacursosjava.dto.CoursePageDTO;
import com.example.escolacursosjava.dto.mapper.CourseMapper;
import com.example.escolacursosjava.exception.RecordNotFoundException;
import com.example.escolacursosjava.model.Course;
import com.example.escolacursosjava.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Validated
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO list() {
        Page<Course> page = courseRepository.findAll(PageRequest.of(0, 10));
        List<CourseDTO> courses = page.get().map(courseMapper::toDTO).toList();
        return new CoursePageDTO(courses, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

//    public List<CourseDTO> list() {
//        return courseRepository.findAll()
//                .stream()
//                .map(courseMapper::toDTO)
//                .toList();
//    }
    public CourseDTO findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }
    public CourseDTO create(@Valid CourseDTO courseDTO){
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(courseDTO)));
    }
    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    Course course = courseMapper.toEntity(courseDTO);
                    recordFound.setName(courseDTO.name());
                    recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
                    recordFound.getLessons().clear();
                    course.getLessons().forEach(recordFound.getLessons()::add);
                    return courseMapper.toDTO(courseRepository.save(recordFound));
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
