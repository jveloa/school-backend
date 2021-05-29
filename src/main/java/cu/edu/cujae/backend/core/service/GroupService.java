package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.GroupDto;

import java.security.acl.Group;
import java.sql.SQLException;
import java.util.List;

public interface GroupService {
    List<GroupDto> getGroupList() throws SQLException;
    void createGroup(GroupDto group) throws SQLException;
    void deleteGroup(int codGroup) throws SQLException;


}
