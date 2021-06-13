package cu.edu.cujae.backend.service;


import cu.edu.cujae.backend.core.dto.SubjectDto;
import cu.edu.cujae.backend.core.dto.YearDto;
import cu.edu.cujae.backend.core.service.CourseService;
import cu.edu.cujae.backend.core.service.SubjectService;
import cu.edu.cujae.backend.core.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class SubjectServiceImp implements SubjectService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private YearService yearService;

    @Autowired
    private CourseService courseService;

    @Override
    public void createSubject(SubjectDto subject) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall("{call create_asignatura(?,?,?)}");
            cs.setString(1, subject.getNameSubject());
            cs.setInt(2, subject.getHours());
            cs.setInt(3, subject.getYear().getCodYear());
            cs.executeUpdate();
        }
    }

    @Override
    public SubjectDto getSubjectById(int codSubject) throws SQLException {
        SubjectDto subject = null;
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM asignatura where cod_asignatura = ? ");
            ps.setInt(1, codSubject);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                subject = new SubjectDto(
                    rs.getInt("cod_asignatura"),
                    rs.getInt("horas"),
                    rs.getString("nombre"),
                    new YearDto(rs.getInt("cod_anno"))
                );
            }
        }

        return subject;
    }


    @Override
    public List<SubjectDto> getSubjects() throws SQLException {
        List<SubjectDto> subjectList = new ArrayList<SubjectDto>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = con.createStatement().
                executeQuery("SELECT * from asignatura");
            while (rs.next()) {
                subjectList.add(new SubjectDto(
                    rs.getInt("cod_asignatura"),
                    rs.getInt("horas"),
                    rs.getString("nombre"),
                    new YearDto(rs.getInt("cod_anno"))
                ));
            }
        }
        setYearData(subjectList);
        return subjectList;
    }

    @Override
    public void deleteSubject(int codSubject) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall("{call delete_asignatura(?)}");
            cs.setInt(1, codSubject);
            cs.executeUpdate();
        }
    }

    @Override
    public void updateSubject(SubjectDto subject) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall("{call update_asignatura(?,?,?,?)}");
            cs.setInt(1, subject.getCodSubject());
            cs.setString(2, subject.getNameSubject());
            cs.setInt(3, subject.getHours());
            cs.setInt(4, subject.getYear().getCodYear());
            cs.executeUpdate();
        }
    }

    private void setYearData(List<SubjectDto> subjects) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.
                executeQuery("SELECT * from anno");
            Map<Integer, String> mapCourses = courseService.getCoursesMap();
            for (SubjectDto subject : subjects) {
                yearService.setData(subject.getYear(), mapCourses, rs);
            }
        }
    }
}
