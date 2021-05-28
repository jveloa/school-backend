package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.DropStudentDto;

import cu.edu.cujae.backend.core.service.DropStudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
@RestController
@RequestMapping("/api/v1/dropStudent")
public class DropStudentController {
    @Autowired
    private DropStudentService dropStudentService;

    @GetMapping("/")
    public ResponseEntity<List<DropStudentDto>> getDropStudent() throws SQLException {
        List<DropStudentDto> dropStudentList = dropStudentService.getDropStudent();
        return ResponseEntity.ok(dropStudentList);
    }

    @PostMapping("/")
    public ResponseEntity<String> createDropStudent(@RequestBody DropStudentDto dropStudent) throws SQLException {
        dropStudentService.createDropStudent(dropStudent);
        return ResponseEntity.ok("Baja insertada");
    }

    @PutMapping("/")
    public ResponseEntity<String> updateDropStudent(@RequestBody DropStudentDto dropStudent) throws SQLException {
        dropStudentService.updateDropStudent(dropStudent);
        return ResponseEntity.ok("Baja Modificada");
    }

    @DeleteMapping("/{codStudent}")
    public ResponseEntity<String> deleteDropStudent(@PathVariable int codStudent) throws SQLException {
        dropStudentService.deleteDropStudent(codStudent);
        return ResponseEntity.ok("Baja Eliminada");
    }

}
