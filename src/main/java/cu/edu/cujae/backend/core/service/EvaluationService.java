package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.EvaluationDto;
import cu.edu.cujae.backend.core.dto.StudentDto;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EvaluationService {
    void createEvaluation(EvaluationDto user) throws SQLException;
    List<EvaluationDto> getEvaluationsByStudent(int cod_student) throws SQLException;
    void deleteEvaluation(int codSubject, int cod_student, int cod_year) throws SQLException;
    void updateEvaluation(EvaluationDto evaluation) throws SQLException;
    List<StudentDto> disapprovedsForYear(int codCourse, int year) throws SQLException;
    List<StudentDto> approvedsForYear(int codCourse, int year) throws SQLException;
}
