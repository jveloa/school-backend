package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.YearDto;
import cu.edu.cujae.backend.core.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/years")
public class YearController {

    @Autowired
    private YearService yearService;

    @GetMapping("/")
    public ResponseEntity<List<YearDto>> getYears() throws SQLException {
        List<YearDto> yearsList = yearService.getYears();
        return ResponseEntity.ok(yearsList);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody YearDto year) throws SQLException {
        yearService.createYear(year);
        return ResponseEntity.ok("Año creado");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody YearDto year) throws SQLException {
        yearService.updateYear(year);
        return ResponseEntity.ok("Año modificado");
    }

    @DeleteMapping("/{codYear}")
    public ResponseEntity<String> delete(@PathVariable int codYear) throws SQLException {
        yearService.deleteYear(codYear);
        return ResponseEntity.ok("Año eliminado");
    }
}
