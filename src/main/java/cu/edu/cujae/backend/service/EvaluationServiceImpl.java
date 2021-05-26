package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.*;
import cu.edu.cujae.backend.core.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createEvaluation(EvaluationDto evaluation) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call create_nota(?, ?, ?, ?)}");
            cs.setInt(1, evaluation.getSubject().getCodSubject());
            cs.setInt(2, evaluation.getStudent().getCodStudent());
            cs.setInt(3, evaluation.getYear().getCodYear());
            cs.setInt(4, evaluation.getRangeEvaluation().getCodEvaluation());
            cs.executeUpdate();
        }
    }

    @Override
    public void deleteEvaluation(int codSubject, int codStudent, int codYear) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs =conn.prepareCall(
                    "{call delete_nota(?, ?, ?)}");

            cs.setInt(1, codSubject);
            cs.setInt(2, codStudent);
            cs.setInt(3, codYear);
        }
    }

    @Override
    public void updateEvaluation(EvaluationDto evaluation) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call update_evaluation(?, ?, ?, ?)}");
            cs.setInt(1, evaluation.getRangeEvaluation().getCodEvaluation());
            cs.setInt(2, evaluation.getSubject().getCodSubject());
            cs.setInt(3, evaluation.getStudent().getCodStudent());
            cs.setInt(4, evaluation.getYear().getCodYear());
            cs.executeUpdate();
        }
    }

    @Override
    public List<EvaluationDto> getEvaluationsByStudent(int codStudent) throws SQLException {
        List<EvaluationDto> evaluations = new ArrayList<EvaluationDto>();
        try(Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            conn.setAutoCommit(false);
            CallableStatement cs = conn.prepareCall("{call notas_por_estudiante(?, ?)}");
            cs.setNull(1, Types.REF, "refcursor");
            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.setInt(2, codStudent);
            cs.execute();
            ResultSet re = (ResultSet) cs.getObject(1);

            while (re.next()){
                evaluations.add(new EvaluationDto(
                         new SubjectDto(re.getInt("cod_asignatura"))
                        ,new StudentDto(re.getInt("cod_estudiante"))
                        ,new YearDto(re.getInt("cod_anno"))
                        ,new RangeEvaluationDto(re.getInt("cod_evaluacion"))
                ));
            }

        return evaluations;
        }
    }
}
