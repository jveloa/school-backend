package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.StudentDto;

import cu.edu.cujae.backend.core.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
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

    @DeleteMapping("{codStudent}")
    public ResponseEntity<String> delete(@PathVariable int codStudent) throws SQLException {
        studentsService.deleteStudents(codStudent);
        return ResponseEntity.ok("Estudiante Eliminado");
    }
}
