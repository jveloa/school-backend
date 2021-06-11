package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.GenderDto;
import cu.edu.cujae.backend.core.dto.MunicipalityDto;
import cu.edu.cujae.backend.core.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/genders")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @GetMapping("/")
    public ResponseEntity<List<GenderDto>> getGenders() throws SQLException {
        List<GenderDto> gendersList = genderService.getGenders();
        return ResponseEntity.ok(gendersList);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody GenderDto gender) throws SQLException {
        genderService.createGender(gender);
        return ResponseEntity.ok("Gender Created");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody GenderDto gender) throws SQLException {
        genderService.updateGender(gender);
        return ResponseEntity.ok("Gender Updated");
    }

    @DeleteMapping("/{codGender}")
    public ResponseEntity<String> delete(@PathVariable int codGender) throws SQLException {
        genderService.deleteGender(codGender);
        return ResponseEntity.ok("Gender Deleted");
    }

    @GetMapping("/{codGender}")
    public ResponseEntity<GenderDto> getSubjectById(@PathVariable int codGender) throws SQLException {
        GenderDto gender = genderService.getGenderById(codGender);
        return ResponseEntity.ok(gender);
    }
}
