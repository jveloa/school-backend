package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.CourseDto;
import cu.edu.cujae.backend.core.dto.GenderDto;
import cu.edu.cujae.backend.core.dto.YearDto;
import cu.edu.cujae.backend.core.service.CourseService;
import cu.edu.cujae.backend.core.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
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
            CallableStatement cs = conn.prepareCall("{call create_anno(?,?)}");
            cs.setInt(1,year.getYearNumber());
            cs.setInt(2,year.getCourse().getCodCourse());
            cs.executeUpdate();
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
            CallableStatement cs = conn.prepareCall("{call delete_anno(?)}");
            cs.setInt(1, codYear);
            cs.executeUpdate();
        }
    }

    @Override
    public void updateYear(YearDto year) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("{call update_anno(?,?,?)}");
            cs.setInt(1, year.getCodYear());
            cs.setInt(2,year.getYearNumber());
            cs.setInt(3, year.getCourse().getCodCourse());
            cs.executeUpdate();
        }
    }

    @Override
    public void setData(YearDto year, Map<Integer,String> mapCourses, ResultSet rs) throws SQLException {
        CourseDto courseDto = new CourseDto();
        rs.first();
        while(true) {
                if (rs.getInt("cod_anno") == year.getCodYear()) {
                year.setYearNumber(rs.getInt("anno"));
                courseDto.setCodCourse(rs.getInt("cod_curso"));
                year.setCourse(courseDto);
                setCourseName(year, mapCourses);
                break;
            }
            rs.next();
        }

    }

    @Override
    public YearDto getYearById(int codYear) throws SQLException {
        YearDto year = null;
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM anno where cod_anno = ? ");
            ps.setInt(1,codYear);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                year = new YearDto(
                        rs.getInt("cod_anno"),
                        rs.getInt("anno"),
                        new CourseDto(rs.getInt("cod_curso"))
                );
            }
        }

        return year;
    }

    @Override
    public void createYearsForNewCourse(int codCourse, int years) throws SQLException {
        for(int i=0; i<years; i++){
            createYear(new YearDto(0, i+1, new CourseDto(codCourse)));
        }
    }

    @Override
    public int getCodAnnoByCourse(int year, int codCOurse) throws SQLException {
        List<YearDto> years = getYears();
        int yearCode=-1;
        for (YearDto i: years) {
            if(i.getYearNumber() == year && i.getCourse().getCodCourse() == codCOurse){
                yearCode = i.getCodYear();
            }
        }
        return yearCode;
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