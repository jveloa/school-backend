package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.CourseDto;

import java.sql.SQLException;
import java.util.List;

public interface CourseService {
    void createCourse(CourseDto Course) throws SQLException;
    List<CourseDto> getCourses() throws SQLException;
    void deleteCourse(int codCourse) throws SQLException;
    void updateCourse(CourseDto Course) throws SQLException;

}
