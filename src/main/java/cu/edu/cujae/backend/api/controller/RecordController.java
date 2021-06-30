package cu.edu.cujae.backend.api.controller;


import cu.edu.cujae.backend.core.dto.RecordDto;
import cu.edu.cujae.backend.core.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping
    public ResponseEntity<List<RecordDto>> getRecord() throws SQLException {
        List<RecordDto> recordList = recordService.getRecord();
        return ResponseEntity.ok(recordList);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody RecordDto record) throws SQLException {
        recordService.createRecord(record);
        return ResponseEntity.ok("Registro insertado");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody RecordDto record) throws SQLException {
        recordService.updateRecord(record);
        return ResponseEntity.ok("Regitro Modificado");
    }

    @DeleteMapping("/{codRecord}")
    public ResponseEntity<String> delete(@PathVariable int codRecord) throws SQLException {
        recordService.deleteRecord(codRecord);
        return ResponseEntity.ok("Registro eliminado");
    }
}
