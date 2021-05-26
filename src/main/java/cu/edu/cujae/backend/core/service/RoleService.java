package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.RoleDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RoleService {
    List<RoleDto> getRolesList() throws SQLException;
    Map<Integer,String> getRolesMap() throws SQLException;
}
