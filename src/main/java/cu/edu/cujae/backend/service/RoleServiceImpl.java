package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.RoleDto;
import cu.edu.cujae.backend.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl<rs> implements RoleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<RoleDto> getRolesList() throws SQLException {
        List<RoleDto> roles = new ArrayList<RoleDto>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = con.createStatement().executeQuery(
                "select * from rol");
            while (rs.next()) {
                roles.add(new RoleDto(
                    rs.getInt("cod_rol"),
                    rs.getString("name")
                ));
            }
        }
        return roles;
    }

    @Override
    public Map<Integer, String> getRolesMap() throws SQLException {
        Map<Integer,String> mapRoles = new HashMap<Integer, String>();
        List<RoleDto> roles = getRolesList();
        for (RoleDto role: roles) {
            mapRoles.put(role.getCodRole(),role.getNameRole());
        }
        return mapRoles;
    }
}

