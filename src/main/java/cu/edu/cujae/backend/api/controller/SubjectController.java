package cu.edu.cujae.backend.api.controller;

import java.sql.SQLException;
import java.util.List;
import cu.edu.cujae.backend.core.service.SubjectService;
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
import cu.edu.cujae.backend.core.dto.SubjectDto;


@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {


    @Autowired
    private SubjectService subjectService;

    @GetMapping("/")
    public ResponseEntity<List<SubjectDto>> getSubjects() throws SQLException {
        List<SubjectDto> subjectList = subjectService.getSubjects();
        return ResponseEntity.ok(subjectList);
    }

    @GetMapping("/{codSubject}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable int codSubject) throws SQLException {
        SubjectDto subject = subjectService.getSubjectById(codSubject);
        return ResponseEntity.ok(subject);
    }

    @GetMapping("/student/{codStudent}")
    public ResponseEntity<List<SubjectDto>> getSubjectByStudent(@PathVariable int codStudent) throws SQLException {
        List<SubjectDto> subjects = subjectService.getSubjectsByStudent(codStudent);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("evaluated/student/{codStudent}")
    public ResponseEntity<List<SubjectDto>> getSubjecsEvaluatedtByStudent(@PathVariable int codStudent) throws SQLException {
        List<SubjectDto> subjects = subjectService.getSubjectsEvaluatedByStudent(codStudent);
        return ResponseEntity.ok(subjects);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody SubjectDto subject) throws SQLException {
        subjectService.createSubject(subject);
        return ResponseEntity.ok("Asignatura insertada");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody SubjectDto subject) throws SQLException {
        subjectService.updateSubject(subject);
        return ResponseEntity.ok("Asignatura Modificada");
    }

    @DeleteMapping("/{codSubject}")
    public ResponseEntity<String> delete(@PathVariable int codSubject) throws SQLException {
        subjectService.deleteSubject(codSubject);
        return ResponseEntity.ok("Asignatura Eliminada");
    }



}

