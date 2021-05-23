package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImp implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void createUser(UserDto user) throws SQLException {
        CallableStatement cs = jdbcTemplate.getDataSource().getConnection().prepareCall(
            "{call create_usuario(?, ?, ?)}");
        cs.setString(1,user.getUsername());
        cs.setString(2,user.getPassword());
        cs.setInt(3,user.getCodRole());
        cs.executeUpdate();
    }

    @Override
    public List<UserDto> getUsers() throws SQLException {
        List<UserDto> users = new ArrayList<UserDto>();
        ResultSet rs = jdbcTemplate.getDataSource().getConnection().createStatement().executeQuery(
            "SELECT * FROM usuario");
        while (rs.next()){
            users.add(new UserDto(
                rs.getInt("cod_usuario"),
                rs.getString("nombre"),
                rs.getString("contrasenna"),
                rs.getInt("cod_rol")
                ));

        }
        return users;
    }

    @Override
    public void deleteUsers(int codUser) throws SQLException {
        CallableStatement cs = jdbcTemplate.getDataSource().getConnection().prepareCall(
            "{call delete_usuario(?)}");
        cs.setInt(1,codUser);
        cs.executeUpdate();
    }

    @Override
    public void updateUser(UserDto user) throws SQLException {
        CallableStatement cs = jdbcTemplate.getDataSource().getConnection().prepareCall(
            "{call update_usuario(?,?)}");
        cs.setInt(1,user.getCodUser());
        cs.setInt(2,user.getCodRole());
        cs.executeUpdate();
    }
}
