package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.reportDto.StudentsByGroupDto;
import cu.edu.cujae.backend.core.dto.reportDto.SubjectsByYearDto;

import java.sql.SQLException;
import java.util.List;

public interface ReportService {

    List<StudentsByGroupDto> getStudentsbyGroup() throws SQLException;

    List<SubjectsByYearDto> getSubjectsByYear() throws SQLException;

}
