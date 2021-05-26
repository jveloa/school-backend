package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.GenderDto;
import cu.edu.cujae.backend.core.dto.ReasonDropDto;
import cu.edu.cujae.backend.core.service.ReasonDropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReasonDropServiceImpl implements ReasonDropService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createReasonDrop(ReasonDropDto reasonDrop) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call create_motivo_baja(?)}");
            cs.setString(1, reasonDrop.getReason());
            cs.executeUpdate();
        }
    }

    @Override
    public List<ReasonDropDto> getReasonsDrop() throws SQLException {
        List<ReasonDropDto> reasons = new ArrayList<ReasonDropDto>();
        ResultSet rs = jdbcTemplate.getDataSource().getConnection().createStatement().executeQuery(
                "SELECT * FROM motivo_baja");
        while (rs.next()){
            reasons.add(new ReasonDropDto(
                    rs.getInt("cod_motivo_baja"),
                    rs.getString("motivo")

            ));

        }
        return reasons;
    }

    @Override
    public void deleteReasonDrop(int codReasonDrop) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs =conn.prepareCall(
                    "{call delete_motivo_baja(?)}");

            cs.setInt(1, codReasonDrop);


        }
    }

    @Override
    public void updateReasonDrop(ReasonDropDto reasonDrop) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call update_motivo_baja(?, ?)}");
            cs.setInt(1, reasonDrop.getCodReason());
            cs.setString(2,reasonDrop.getReason());

            cs.executeUpdate();
        }
    }
}
