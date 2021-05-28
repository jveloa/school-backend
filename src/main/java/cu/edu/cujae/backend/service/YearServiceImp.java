package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.CourseDto;
import cu.edu.cujae.backend.core.dto.YearDto;
import cu.edu.cujae.backend.core.service.CourseService;
import cu.edu.cujae.backend.core.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class YearServiceImp implements YearService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseService courseService;


    @Override
    public void createYear(YearDto year) throws SQLException {
        try(Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("create_anno(?,?)");
            cs.setInt(1,year.getYearNumber());
            cs.setInt(2,year.getCourse().getCodCourse());
        }
    }

    @Override
    public List<YearDto> getYears() throws SQLException {
        List<YearDto> yearList = new ArrayList<YearDto>();
        ResultSet rs = jdbcTemplate.getDataSource().getConnection().createStatement().
                executeQuery("SELECT * from anno");
        while (rs.next()){
            yearList.add(new YearDto(
                    rs.getInt("cod_anno"),
                    rs.getInt("anno"),
                    new CourseDto(rs.getInt("cod_curso"))
            ));
        }
        setCoursesNames(yearList);
        return yearList;
    }

    @Override
    public void deleteYear(int codYear) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("delete_anno(?)");
            cs.setInt(1, codYear);
        }
    }

    @Override
    public void updateYear(YearDto year) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("update_anno");
            cs.setInt(1, year.getCodYear());
        }
    }

    @Override
    public void setData(YearDto year) throws SQLException {
        CourseDto courseDto = new CourseDto();
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            ResultSet rs= conn.createStatement().
                    executeQuery("SELECT * from anno WHERE anno.cod_anno = " + year.getCodYear() );
            rs.next();
            year.setYearNumber(rs.getInt("anno"));
            courseDto.setCodCourse(rs.getInt("cod_curso"));
            year.setCourse(courseDto);
            Map<Integer,String> mapCourses = courseService.getCoursesMap();
            setCourseName(year, mapCourses);
        }
    }

    private void setCourseName(YearDto year, Map<Integer,String> mapCourses) {
        year.getCourse().setCourse(mapCourses.get(year.getCourse().getCodCourse()));
    }

    private void setCoursesNames(List<YearDto> years) throws SQLException {
        Map<Integer,String> mapCourses = courseService.getCoursesMap();
        for (YearDto year: years) {
            setCourseName(year, mapCourses);
        }
    }

}