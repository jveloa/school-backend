package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.reportDto.EvalByGroupDto;
import cu.edu.cujae.backend.core.dto.reportDto.StudentLadderDto;
import cu.edu.cujae.backend.core.dto.reportDto.StudentsByGroupDto;
import cu.edu.cujae.backend.core.dto.reportDto.SubjectsByYearDto;

import java.sql.SQLException;
import java.util.List;

public interface ReportService {

    List<StudentsByGroupDto> getStudentsbyGroup() throws SQLException;

    List<SubjectsByYearDto> getSubjectsByYear() throws SQLException;

    List<StudentLadderDto> getStudentLadder(String curso, int anno) throws  SQLException;

    List<EvalByGroupDto> getEvalByGroupDtoList() throws SQLException;

}
