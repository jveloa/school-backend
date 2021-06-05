package cu.edu.cujae.backend.service;
import cu.edu.cujae.backend.core.dto.RoleDto;
import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.service.RoleService;
import cu.edu.cujae.backend.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImp implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleService roleService;

    @Override
    public List<UserDto> getUsers() throws SQLException {
        List<UserDto> users = new ArrayList<UserDto>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM usuario");
            while (rs.next()) {
                users.add(new UserDto(
                    rs.getInt("cod_usuario"),
                    rs.getString("nombre"),
                    rs.getString("contrasenna"),
                    new RoleDto(rs.getInt("cod_rol"),"")
                ));
            }
            changeCodRolebyNameRole(users);
            return users;
        }
    }

    @Override
    public void createUser(UserDto user) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = con.prepareCall("{call create_usuario(?,?,?)}");
            cs.setString(1, user.getUsername());
            cs.setString(2, encodePass(user.getPassword()));
            cs.setInt(3, user.getRole().getCodRole());
            cs.executeUpdate();
        }
    }

    @Override
    public void updateUser(UserDto user) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("update usuario set cod_rol = ? where cod_usuario = ?");
            ps.setInt(1, user.getRole().getCodRole());
            ps.setInt(2, user.getCodUser());
            ps.executeUpdate();
        }
    }


    @Override
    public void deleteUsers(int codUser) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = con.prepareCall(
                "{call delete_usuario(?)}");
            cs.setInt(1, codUser);
            cs.executeUpdate();
        }
    }

    @Override
    public UserDto getUserByUsername(String username) throws SQLException {
        UserDto user = null;
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario where nombre = ? ");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            rs.next();
                user = new UserDto(
                    rs.getInt("cod_usuario"),
                    rs.getString("nombre"),
                    rs.getString("contrasenna"),
                    new RoleDto(rs.getInt("cod_rol"))
                    );
        }

        return user;
    }

    @Override
    public UserDto getUserById(int codUser) throws SQLException {
        UserDto user = null;
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario where cod_usuario = ? ");
            ps.setInt(1,codUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                user = new UserDto(
                    rs.getInt("cod_usuario"),
                    rs.getString("nombre"),
                    rs.getString("contrasenna"),
                    new RoleDto(rs.getInt("cod_rol"))
                );
            }
        }

        return user;
    }

    private String getMd5Hash(String password) {
        MessageDigest md;
        String md5Hash = "";
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            md5Hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return md5Hash;
    }

    private void changeCodRolebyNameRole(List<UserDto> users) throws SQLException {
        Map<Integer,String> mapRoles=  roleService.getRolesMap();
        for (UserDto user: users) {
            user.getRole().setNameRole( mapRoles.get(user.getRole().getCodRole()));
        }
    }

    private String encodePass(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }



}


