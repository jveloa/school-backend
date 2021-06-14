package cu.edu.cujae.backend.api.controller;


import cu.edu.cujae.backend.core.dto.EvaluationDto;
import cu.edu.cujae.backend.core.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/{codStudent}")
    public ResponseEntity<List<EvaluationDto>> getUsers(@PathVariable int codStudent) throws SQLException {
        List<EvaluationDto> evaluationList = evaluationService.getEvaluationsByStudent(codStudent);
        return ResponseEntity.ok(evaluationList);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody EvaluationDto evaluation) throws SQLException {
        evaluationService.createEvaluation(evaluation);
        return ResponseEntity.ok("Evaluation Created");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody EvaluationDto evaluation) throws SQLException {
        evaluationService.updateEvaluation(evaluation);
        return ResponseEntity.ok("Evaluation Updated");
    }

    @DeleteMapping("/{codSubject}/{codStudent}/{codYear}")
    public ResponseEntity<String> delete(@PathVariable("codSubject") int codSubjetc, @PathVariable("codStudent") int codStudent, @PathVariable("codYear") int codYear ) throws SQLException {
        evaluationService.deleteEvaluation(codSubjetc, codStudent, codYear);
        return ResponseEntity.ok("Evaluation Deleted");
    }

}
