package cu.edu.cujae.backend.service;

import com.sun.org.apache.bcel.internal.generic.Select;
import cu.edu.cujae.backend.core.dto.CourseDto;
import cu.edu.cujae.backend.core.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CourseServiceImp implements CourseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createCourse(CourseDto course) throws SQLException {

        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call create_curso(?)}");
            cs.setString(1, course.getCourse());
            cs.executeUpdate();
        }
    }

    @Override
    public List<CourseDto> getCourses() throws SQLException {
        List<CourseDto> courseList = new ArrayList<CourseDto>();
        ResultSet rs = jdbcTemplate.getDataSource().getConnection().createStatement().executeQuery(
                "Select * from curso"
        );
        while (rs.next()){
            courseList.add(new CourseDto(
                    rs.getInt("cod_curso"),
                    rs.getString("curso")
            ));
        }
        return courseList;
    }

    @Override
    public void deleteCourse(int codCourse) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("delete_curso(?)");
            cs.setInt(1,codCourse);
        }
    }

    @Override
    public void updateCourse(CourseDto course) throws SQLException {
        try(Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("update_curso(?,?)");
            cs.setInt(1,course.getCodCourse());
            cs.setString(2, course.getCourse());
        }
    }

    @Override
    public Map<Integer, String> getCoursesMap() throws SQLException {
        Map<Integer, String> mapCourses = new HashMap<Integer, String>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = con.createStatement().executeQuery(
                    "select * from curso");
            while (rs.next()) {
                mapCourses.put(rs.getInt("cod_curso"), rs.getString("curso"));
            }
            return mapCourses;
        }
    }
}