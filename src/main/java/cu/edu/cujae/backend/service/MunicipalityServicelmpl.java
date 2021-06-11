package cu.edu.cujae.backend.service;


import cu.edu.cujae.backend.core.dto.MunicipalityDto;
import cu.edu.cujae.backend.core.dto.StudentDto;
import cu.edu.cujae.backend.core.service.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
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
    public List<MunicipalityDto> getMunicipalities() throws SQLException {
        List<MunicipalityDto> municipalitys = new ArrayList<MunicipalityDto>();
        ResultSet rs = jdbcTemplate.getDataSource().getConnection().createStatement().executeQuery(
                "SELECT * FROM municipio");
        while (rs.next()){
            municipalitys.add(new MunicipalityDto(
                    rs.getInt("cod__municipio"),
                    rs.getString("nombre")


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

    @Override
    public MunicipalityDto getMunicipalityById(int codMunicipality) throws SQLException {
        MunicipalityDto municipality = null;
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM municipio where cod__municipio = ? ");
            ps.setInt(1,codMunicipality);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                municipality = new MunicipalityDto(
                        rs.getInt("cod__municipio"),
                        rs.getString("nombre")

                );
            }
        }
        return municipality;
    }
}
