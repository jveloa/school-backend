package cu.edu.cujae.backend.api.controller;

import cu.edu.cujae.backend.core.dto.reportDto.StudentsByGroupDto;
import cu.edu.cujae.backend.core.dto.reportDto.SubjectsByYearDto;
import cu.edu.cujae.backend.core.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/studentsByGroup")
    public ResponseEntity<List<StudentsByGroupDto>> getStudentsByGroup() throws SQLException {
        List<StudentsByGroupDto> list = reportService.getStudentsbyGroup();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/subjectsByYear")
    public ResponseEntity<List<SubjectsByYearDto>> getSubjectsByYear() throws SQLException{
        List<SubjectsByYearDto> list = reportService.getSubjectsByYear();
        return ResponseEntity.ok(list);
    }
}
