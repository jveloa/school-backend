package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.SubjectDto;

import java.sql.SQLException;
import java.util.List;

public interface SubjectService {

    void createSubject(SubjectDto Subject) throws SQLException;
    List<SubjectDto> getSubjects() throws SQLException;
    void deleteSubject(int codSubject) throws SQLException;
    void updateSubject(SubjectDto Subject) throws SQLException;

}
