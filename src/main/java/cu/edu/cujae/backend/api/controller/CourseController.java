package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.CourseDto;
import cu.edu.cujae.backend.core.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public ResponseEntity<List<CourseDto>> getCourses() throws SQLException {
        List<CourseDto> coursesList = courseService.getCourses();
        return ResponseEntity.ok(coursesList);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody CourseDto course) throws SQLException {
        courseService.createCourse(course);
        return ResponseEntity.ok("Curso creado");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody CourseDto course) throws SQLException {
        courseService.updateCourse(course);
        return ResponseEntity.ok("Curso modificado");
    }

    @DeleteMapping("/{codCourse}")
    public ResponseEntity<String> delete(@PathVariable int codCourse) throws SQLException {
        courseService.deleteCourse(codCourse);
        return ResponseEntity.ok("Curso eliminado");
    }
}
