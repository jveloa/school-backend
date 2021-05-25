package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.EvaluationDto;
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
            cs.setInt(1, evaluation.getCodSubject());
            cs.setInt(2, evaluation.getCodStudent());
            cs.setInt(3, evaluation.getCodYear());
            cs.setInt(4, evaluation.getCodEvaluation());
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
            cs.setInt(1, evaluation.getCodEvaluation());
            cs.setInt(2, evaluation.getCodSubject());
            cs.setInt(3, evaluation.getCodStudent());
            cs.setInt(4, evaluation.getCodYear());
            cs.executeUpdate();
        }
    }

    @Override
    public List<EvaluationDto> getEvaluationsByStudent(int codStudent) throws SQLException {
        List<EvaluationDto> evaluations = new ArrayList<EvaluationDto>();
        try(Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall("{call notas_por_estudiantes(?, ?)}");

            cs.setNull(1, Types.REF, "refcursor");
            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.setInt(2, codStudent);
            cs.execute();
            ResultSet re = (ResultSet) cs.getObject(1);

            while (re.next()){
                evaluations.add(new EvaluationDto(re.getInt("cod_asignatura")
                        ,re.getInt("cod_estudiante")
                        ,re.getInt("cod_anno")
                        ,re.getInt("cod_evaluacion")));
            }

        return evaluations;
        }
    }
}
