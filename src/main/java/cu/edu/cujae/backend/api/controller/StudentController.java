package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.StudentDto;

import cu.edu.cujae.backend.core.service.StudentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {


    private StudentsService studentsService;

    @GetMapping("/")
    public ResponseEntity<List<StudentDto>> getStudents() throws SQLException {
        List<StudentDto> studentsList = studentsService.getStudents();
        return ResponseEntity.ok(studentsList);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody StudentDto students) throws SQLException {
        studentsService.createStudents(students);
        return ResponseEntity.ok("Estudiante insertado");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody StudentDto students) throws SQLException {
        studentsService.updateStudents(students);
        return ResponseEntity.ok("Estudiante Modificado");
    }

    @DeleteMapping("/{codStudent}")
    public ResponseEntity<String> delete(@PathVariable int codStudents) throws SQLException {
        studentsService.deleteStudents(codStudents);
        return ResponseEntity.ok("Estudiante Eliminado");
    }
}
