package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.CourseDto;
import cu.edu.cujae.backend.core.dto.YearDto;
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


@Service
public class YearServiceImp implements YearService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
}