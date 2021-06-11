package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.MunicipalityDto;
import cu.edu.cujae.backend.core.dto.SubjectDto;
import cu.edu.cujae.backend.core.service.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
@RestController
@RequestMapping("/api/v1/municipality")
public class MunicipalityController {

    @Autowired
    private  MunicipalityService municipalityService;

    @GetMapping("/")
    public ResponseEntity<List<MunicipalityDto>> getMunicipalities() throws SQLException {
        List<MunicipalityDto> MunicipalityList = municipalityService.getMunicipalities();
        return ResponseEntity.ok(MunicipalityList);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody MunicipalityDto municipality) throws SQLException {
        municipalityService.createMunicipality(municipality);
        return ResponseEntity.ok("Municipality Created");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody MunicipalityDto municipality) throws SQLException {
        municipalityService.updateMunicipality(municipality);
        return ResponseEntity.ok("Municipality Updated");
    }

    @DeleteMapping("/{codGender}")
    public ResponseEntity<String> delete(@PathVariable int codMunicipality) throws SQLException {
        municipalityService.deleteMunicipality(codMunicipality);
        return ResponseEntity.ok("Municipality Deleted");
    }

    @GetMapping("/{codMunicipality}")
    public ResponseEntity<MunicipalityDto> getSubjectById(@PathVariable int codMunicipality) throws SQLException {
        MunicipalityDto municipality = municipalityService.getMunicipalityById(codMunicipality);
        return ResponseEntity.ok(municipality);
    }
}
