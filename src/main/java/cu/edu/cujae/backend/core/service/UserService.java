package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void createUser(UserDto user) throws SQLException;
    List<UserDto> getUsers() throws SQLException;
    void deleteUsers(int codUser) throws SQLException;
    void updateUser(UserDto user) throws SQLException;




}
