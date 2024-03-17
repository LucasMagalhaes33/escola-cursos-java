package com.example.escolacursosjava.dto.mapper;

import com.example.escolacursosjava.dto.CourseDTO;
import com.example.escolacursosjava.enums.Category;
import com.example.escolacursosjava.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseDTO(course.getId(),
                course.getName(),
                course.getCategory().getValue(),
                course.getLessons());
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null) {
            return null;
        }
        Course course = new Course();
        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertCategoryValue(courseDTO.category()));
        return course;
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "Back-end":
                return Category.BACK_END;
            case "Front-end":
                return Category.FRONT_END;
            default:
                throw new IllegalArgumentException();
        }
    }
}
