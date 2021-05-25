package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.GenderDto;
import cu.edu.cujae.backend.core.dto.ReasonDropDto;
import cu.edu.cujae.backend.core.service.GenderService;
import cu.edu.cujae.backend.core.service.ReasonDropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/reasonsDrop")
public class ReasonDropController {

    @Autowired
    private ReasonDropService reasonDropService;

    @GetMapping("/")
    public ResponseEntity<List<ReasonDropDto>> getGenders() throws SQLException {
        List<ReasonDropDto> reasonsDropList = reasonDropService.getReasonsDrop();
        return ResponseEntity.ok(reasonsDropList);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody ReasonDropDto reason) throws SQLException {
        reasonDropService.createReasonDrop(reason);
        return ResponseEntity.ok("ReasonDrop Created");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody ReasonDropDto reason) throws SQLException {
        reasonDropService.updateReasonDrop(reason);
        return ResponseEntity.ok("ReasonDrop Updated");
    }

    @DeleteMapping("/{codReasonDrop}")
    public ResponseEntity<String> delete(@PathVariable int codReasonDrop) throws SQLException {
        reasonDropService.deleteReasonDrop(codReasonDrop);
        return ResponseEntity.ok("Reason Deleted");
    }
}
