package cu.edu.cujae.backend.service;

import com.sun.org.apache.bcel.internal.generic.Select;
import cu.edu.cujae.backend.core.dto.CourseDto;
import cu.edu.cujae.backend.core.dto.YearDto;
import cu.edu.cujae.backend.core.service.CourseService;
import cu.edu.cujae.backend.core.service.GroupService;
import cu.edu.cujae.backend.core.service.SubjectService;
import cu.edu.cujae.backend.core.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CourseServiceImp implements CourseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private YearService yearService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private SubjectService subjectService;

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
    public ArrayList<CourseDto> getCourses() throws SQLException {
        ArrayList<CourseDto> courseList = new ArrayList<CourseDto>();
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
            CallableStatement cs = conn.prepareCall("{call delete_curso(?)}");
            cs.setInt(1,codCourse);
            cs.executeUpdate();
        }
    }

    @Override
    public void updateCourse(CourseDto course) throws SQLException {
        try(Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("{call update_curso(?,?)}");
            cs.setInt(1,course.getCodCourse());
            cs.setString(2, course.getCourse());
            cs.executeUpdate();
        }
    }

    @Override
    public CourseDto getCourseById(int codCourse) throws SQLException {
        CourseDto course = null;
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM curso where cod_curso = ? ");
            ps.setInt(1,codCourse);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                course = new CourseDto(
                        rs.getInt("cod_curso"),
                        rs.getString("curso")

                );
            }
        }

        return course;
    }

    @Override
    public void nextCourse() throws SQLException {
        try {
            ArrayList<CourseDto> courses = getCourses();
            //Se crea el nuevo curso
            createCourse(new CourseDto(0, "prueba"));
            //Se crean los años para el nuevo curso
            yearService.createYearsForNewCourse(getCourses().get(getCourses().size() - 1).getCodCourse(), 4);
            //Se crean los grupos del nuevo curso
            groupService.createNewCourseGroups(getCourses().get(getCourses().size() - 1).getCodCourse(), getCourses().get(getCourses().size() - 2).getCodCourse(), 4);
            //Se llenan los estudiantes
            groupService.fillGroupsWithStudents(4, getCourses().get(getCourses().size() - 1).getCodCourse(), getCourses().get(getCourses().size() - 2).getCodCourse());
            //Se crean las asignaturas
            subjectService.createSubjectsNewCourse(4);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
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