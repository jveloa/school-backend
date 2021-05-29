package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.GroupDto;
import cu.edu.cujae.backend.core.dto.YearDto;
import cu.edu.cujae.backend.core.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImp implements GroupService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GroupDto> getGroupList() throws SQLException {
        List<GroupDto> groupList = new ArrayList<GroupDto>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from grupo");
            while (rs.next()) {
                groupList.add(new GroupDto(
                    rs.getInt("cod_grupo"),
                    new YearDto(rs.getInt("cod_anno")),
                    rs.getInt("numero")
                ));
            }

        }
        return groupList;
    }

    @Override
    public void createGroup(GroupDto group) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = con.prepareCall("{call create_grupo(?,?)}");
            cs.setInt(1,group.getNumberGroup());
            cs.setInt(2,group.getYear().getCodYear());
            cs.executeUpdate();

        }
    }

    @Override
    public void deleteGroup(int codGroup) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = con.prepareCall("{call delete_grupo(?)}");
            cs.setInt(1,codGroup);
            cs.executeUpdate();
        }
    }
}



















