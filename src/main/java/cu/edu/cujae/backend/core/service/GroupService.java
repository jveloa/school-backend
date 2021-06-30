package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.GroupDto;

import java.security.acl.Group;
import java.sql.SQLException;
import java.util.List;

public interface GroupService {
    List<GroupDto> getGroupList() throws SQLException;
    void createGroup(GroupDto group) throws SQLException;
    void deleteGroup(int codGroup) throws SQLException;
    List<GroupDto> getGroupsLastCourse() throws SQLException;
    boolean isAssignmentsGroup(int codGroup) throws SQLException;
    GroupDto getGropByID(int cod)throws SQLException;


    void createNewCourseGroups(int codCourse, int codLastCourse, int years) throws SQLException;
    void fillGroupsWithStudents(int years, int codCourse, int codLastCourse)throws SQLException;
    List<GroupDto> getGroupsByCourseAndYear(int codCourse, int year)throws SQLException;
}
