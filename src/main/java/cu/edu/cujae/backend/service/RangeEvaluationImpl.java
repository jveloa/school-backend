package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.CourseDto;
import cu.edu.cujae.backend.core.dto.RangeEvaluationDto;
import cu.edu.cujae.backend.core.dto.YearDto;
import cu.edu.cujae.backend.core.service.RangeEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rangeEvaluations")
public class RangeEvaluationImpl implements RangeEvaluationService {



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createEvaluation(RangeEvaluationDto rangeEvaluation) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall(
                    "{call create(?, ?)}");
            cs.setInt(1, rangeEvaluation.getCodEvaluation());
            cs.setString(2, rangeEvaluation.getEvaluation());
            cs.executeUpdate();
        }
    }

    @Override
    public List<RangeEvaluationDto> getRangeEvaluations() throws SQLException {
        List<RangeEvaluationDto> evaluations = new ArrayList<RangeEvaluationDto>();
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = jdbcTemplate.getDataSource().getConnection().createStatement().executeQuery(
                    "SELECT * FROM evaluacion");
            while (rs.next()) {
                evaluations.add(new RangeEvaluationDto(
                        rs.getInt("cod_evaluacion"),
                        rs.getString("evaluacion")
                ));
            }
            return evaluations;
        }
    }


    @Override
    public void deleteEvaluation(int codEvaluation) throws SQLException {
            try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
                CallableStatement cs =conn.prepareCall(
                        "{call delete_evaluacion(?)}");

                cs.setInt(1, codEvaluation);

            }
    }

    @Override
    public void updateEvaluation(RangeEvaluationDto evaluation) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call update_evaluacion(?, ?)}");
            cs.setInt(1, evaluation.getCodEvaluation());
            cs.setString(2, evaluation.getEvaluation());
            cs.executeUpdate();
        }
    }

    @Override
    public RangeEvaluationDto getRangeEvaluationById(int codEvaluation) throws SQLException {
        RangeEvaluationDto rangeEvaluation = null;
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM evaluacion where cod_evaluacion = ? ");
            ps.setInt(1,codEvaluation);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                rangeEvaluation = new RangeEvaluationDto(
                        rs.getInt("cod_evaluacion"),
                        rs.getString("evaluacion")
                );
            }
        }
        return rangeEvaluation;
    }
}

