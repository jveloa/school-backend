package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.YearDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface YearService {
    void createYear(YearDto Year) throws SQLException;
    List<YearDto> getYears() throws SQLException;
    void deleteYear(int codYear) throws SQLException;
    void updateYear(YearDto Year) throws SQLException;

    void setData(YearDto year, Map<Integer, String> mapCourses, ResultSet rs) throws SQLException;
}
