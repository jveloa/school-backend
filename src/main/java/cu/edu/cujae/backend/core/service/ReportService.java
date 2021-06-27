package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.reportDto.StudentForGroupDto;

import java.sql.SQLException;
import java.util.List;

public interface ReportService {
    List<StudentForGroupDto> getStudentForGroup() throws SQLException;
}
