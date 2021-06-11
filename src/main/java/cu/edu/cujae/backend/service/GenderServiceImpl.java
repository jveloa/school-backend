package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.GenderDto;
import cu.edu.cujae.backend.core.dto.MunicipalityDto;
import cu.edu.cujae.backend.core.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createGender(GenderDto gender) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call create_sexo(?)}");
            cs.setInt(1, gender.getCodGender());
            cs.executeUpdate();
        }
    }

    @Override
    public List<GenderDto> getGenders() throws SQLException {
        List<GenderDto> genders = new ArrayList<GenderDto>();
        ResultSet rs = jdbcTemplate.getDataSource().getConnection().createStatement().executeQuery(
                "SELECT * FROM sexo");
        while (rs.next()){
            genders.add(new GenderDto(
                    rs.getInt("cod_sexo"),
                    rs.getString("nombre_sexo")

            ));

        }
        return genders;
    }

    @Override
    public void deleteGender(int codGender) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs =conn.prepareCall(
                    "{call delete_sexo(?)}");

            cs.setInt(1, codGender);


        }
    }

    @Override
    public void updateGender(GenderDto gender) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call update_evaluation(?, ?, ?, ?)}");
            cs.setInt(1, gender.getCodGender());
            cs.setString(2,gender.getGender());

            cs.executeUpdate();
        }
    }

    @Override
    public GenderDto getGenderById(int codGender) throws SQLException {
        GenderDto gender = null;
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM sexo where cod_sexo = ? ");
            ps.setInt(1,codGender);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                gender = new GenderDto(
                        rs.getInt("cod_sexo"),
                        rs.getString("nombre_sexo")

                );
            }
        }
        return gender;
    }
}
