package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.RangeEvaluationDto;
import cu.edu.cujae.backend.core.dto.YearDto;
import cu.edu.cujae.backend.core.service.RangeEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rangeEvaluations")
public class RangeEvaluationController {

    @Autowired
    private RangeEvaluationService rangeEvaluationService;

    @GetMapping("/")
    public ResponseEntity<List<RangeEvaluationDto>> getUsers() throws SQLException {
        List<RangeEvaluationDto> evaluationRange = rangeEvaluationService.getRangeEvaluations();
        return ResponseEntity.ok(evaluationRange);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody RangeEvaluationDto evaluation) throws SQLException {
        rangeEvaluationService.createEvaluation(evaluation);
        return ResponseEntity.ok("Evaluation Created");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody RangeEvaluationDto evaluation) throws SQLException {
        rangeEvaluationService.updateEvaluation(evaluation);
        return ResponseEntity.ok("Evaluation Updated");
    }

    @DeleteMapping("/{codEvaluation}")
    public ResponseEntity<String> delete(@PathVariable int codEvaluation) throws SQLException {
        rangeEvaluationService.deleteEvaluation(codEvaluation);
        return ResponseEntity.ok("Evaluation Deleted");
    }

    @GetMapping("/{codEvaluation}")
    public ResponseEntity<RangeEvaluationDto> getRangeEvaluationById(@PathVariable int codEvaluation) throws SQLException {
        RangeEvaluationDto rangeEvaluation = rangeEvaluationService.getRangeEvaluationById(codEvaluation);
        return ResponseEntity.ok(rangeEvaluation);
    }
}
