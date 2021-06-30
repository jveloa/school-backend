package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.*;
import cu.edu.cujae.backend.core.service.CourseService;
import cu.edu.cujae.backend.core.service.GroupService;
import cu.edu.cujae.backend.core.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImp implements GroupService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private YearService yearService;
    @Autowired
    private CourseService courseService;

    @Override
    public List<GroupDto> getGroupsList() throws SQLException {
        List<GroupDto> groupList = new ArrayList<GroupDto>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from grupo");
            while (rs.next()) {
                groupList.add(new GroupDto(
                    rs.getInt("cod_grupo"),
                    new YearDto(rs.getInt("cod_anno"),0,new CourseDto()),
                    rs.getInt("numero")
                ));
            }

        }
        setYearData(groupList);
        return groupList;
    }
    @Override
    public GroupDto getGropByID(int cod)throws SQLException{
        GroupDto group = null;

        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM grupo where cod_grupo = ? ");
            ps.setInt(1,cod);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                group = new GroupDto(
                        rs.getInt("cod_grupo"),
                       yearService.getYearById(rs.getInt("cod_anno")) ,
                        rs.getInt("numero"));



            }
        }

        return group;
    }

    @Override
    public List<GroupDto> getGroupsLastCourse() throws SQLException {
        List<GroupDto> groupList = new ArrayList<GroupDto>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            con.setAutoCommit(false);
            CallableStatement cs = con.prepareCall("{call grupo_service_devolver_grupos_ultimo_curso(?)}");
            cs.setNull(1,Types.REF,"refcursor");
            cs.registerOutParameter(1,Types.REF_CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                groupList.add(new GroupDto(
                    rs.getInt("cod_grupo"),
                    new YearDto(rs.getInt("cod_anno"),0,new CourseDto()),
                    rs.getInt("numero")
                ));
            }
        }
        setYearData(groupList);
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

    @Override
    public boolean isAssignmentsGroup(int codGroup) throws SQLException {
        boolean isAssignments;
        List<Integer> intList = new ArrayList<>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()){
            con.setAutoCommit(false);
            CallableStatement cs = con.prepareCall("{call is_asignaciones_grupo(?,?)}");
            cs.setInt(1,codGroup);
            cs.setNull(2,Types.REF,"refcursor");
            cs.registerOutParameter(2,Types.REF_CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(2);
            return rs.next() ? true : false;
        }
    }

    private void setYearData(List< GroupDto > groups) throws SQLException {
            try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * from anno");
                Map<Integer, String> mapCourses = courseService.getCoursesMap();
                for (GroupDto group : groups) {
                    yearService.setData(group.getYear(), mapCourses, rs);
                }
            }
    }
}



















