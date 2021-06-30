package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.SubjectDto;
import cu.edu.cujae.backend.core.dto.YearDto;

import java.sql.SQLException;
import java.util.List;

public interface SubjectService {

    void createSubject(SubjectDto Subject) throws SQLException;
    void createSubject2(SubjectDto Subject) throws SQLException;
    SubjectDto getSubjectById(int codSubject) throws SQLException;
    List<SubjectDto> getSubjects() throws SQLException;
    void deleteSubject(int codSubject) throws SQLException;
    void updateSubject(SubjectDto Subject) throws SQLException;
    List<SubjectDto> getSubjectsByStudent(int codStudent) throws SQLException;
    List<SubjectDto> getSubjectsEvaluatedByStudent(int codStudent) throws SQLException;
    void createSubjectsNewCourse(int years) throws SQLException;
    List<SubjectDto> getSubjectsByYear(int codCourse, int year) throws SQLException;
}
