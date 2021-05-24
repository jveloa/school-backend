package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.EvaluationDto;
import cu.edu.cujae.backend.core.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface EvaluationService {
    void createEvaluation(EvaluationDto user) throws SQLException;
    void deleteEvaluation(int codEvaluation) throws SQLException;
    void updateEvaluation(EvaluationDto evaluation) throws SQLException;

}
