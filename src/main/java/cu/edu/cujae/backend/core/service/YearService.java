package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.YearDto;

import java.sql.SQLException;
import java.util.List;

public interface YearService {
    void createYear(YearDto Year) throws SQLException;
    List<YearDto> getYears() throws SQLException;
    void deleteYear(int codYear) throws SQLException;
    void updateYear(YearDto Year) throws SQLException;

}
