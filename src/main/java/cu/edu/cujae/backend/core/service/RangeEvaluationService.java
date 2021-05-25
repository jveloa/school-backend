package cu.edu.cujae.backend.core.service;


import cu.edu.cujae.backend.core.dto.RangeEvaluationDto;

import java.sql.SQLException;
import java.util.List;

public interface RangeEvaluationService {
    void createEvaluation(RangeEvaluationDto rangeEvaluation) throws SQLException;
    List<RangeEvaluationDto> getRangeEvaluations() throws SQLException;
    void deleteEvaluation(int codEvaluation) throws SQLException;
    void updateEvaluation(RangeEvaluationDto evaluation) throws SQLException;

}
