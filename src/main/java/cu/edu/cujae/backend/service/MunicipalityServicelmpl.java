package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.GenderDto;
import cu.edu.cujae.backend.core.dto.MunicipalityDto;
import cu.edu.cujae.backend.core.service.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MunicipalityServicelmpl implements MunicipalityService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createMunicipality(MunicipalityDto municipality) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call create_municipio(?)}");
            cs.setString(1, municipality.getMunicipality());
            cs.executeUpdate();
        }
    }

    @Override
    public List<MunicipalityDto> getMunicipalitys() throws SQLException {
        List<MunicipalityDto> municipalitys = new ArrayList<MunicipalityDto>();
        ResultSet rs = jdbcTemplate.getDataSource().getConnection().createStatement().executeQuery(
                "SELECT * FROM municipio");
        while (rs.next()){
            municipalitys.add(new MunicipalityDto(
                    rs.getInt("cod_municipio"),
                    rs.getString("municipio")


            ));

        }
        return municipalitys;
    }

    @Override
    public void deleteMunicipality(int codMunicipality) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs =conn.prepareCall(
                    "{call delete_municipio(?)}");

            cs.setInt(1, codMunicipality);


        }
    }

    @Override
    public void updateMunicipality(MunicipalityDto municipality) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call update_municipio(?, ?)}");
            cs.setInt(1, municipality.getCodMunicipality());
            cs.setString(2,municipality.getMunicipality());

            cs.executeUpdate();
        }
    }
}
