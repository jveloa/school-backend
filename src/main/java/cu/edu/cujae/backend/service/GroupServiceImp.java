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
    public List<GroupDto> getGroupList() throws SQLException {
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
    public void createGroup(GroupDto group) throws SQLException {
       try (Connection con = jdbcTemplate.getDataSource().getConnection()){
            ResultSet rs = con.createStatement().executeQuery(
                    "Select cod_anno from anno where cod_curso = "+ group.getYear().getCourse().getCodCourse() +
                            "and anno =" + group.getYear().getYearNumber());
            rs.next();
            CallableStatement cs = con.prepareCall("{call create_grupo(?,?)}");
            cs.setInt(1,group.getNumberGroup());
            cs.setInt(2,rs.getInt("cod_anno"));
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

    private void setYearObject(List<GroupDto> groups) throws SQLException {
       int i=0;
        while ( i < groups.size()) {
            List<YearDto>years=yearService.getYears();
            for (int j=0;j<years.size();j++){
            if(groups.get(i).getYear().getCodYear()==years.get(j).getCodYear()){
                groups.get(i).getYear().setYearNumber(years.get(j).getYearNumber());
                groups.get(i).getYear().getCourse().setCodCourse(years.get(j).getCourse().getCodCourse());
                groups.get(i).getYear().getCourse().setCourse(years.get(j).getCourse().getCourse());
            }
            }
             i++;
        }
    }

    private void setYearData(List< GroupDto > groups) throws SQLException {
            try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.
                        executeQuery("SELECT * from anno");
                Map<Integer, String> mapCourses = courseService.getCoursesMap();
                for (GroupDto group : groups) {
                    yearService.setData(group.getYear(), mapCourses, rs);
                }
            }
    }
}



















