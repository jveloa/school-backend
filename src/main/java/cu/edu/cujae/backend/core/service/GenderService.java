package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.GenderDto;

import java.sql.SQLException;
import java.util.List;

public interface GenderService {
    void createGender(GenderDto gender) throws SQLException;
    List<GenderDto> getGenders() throws SQLException;
    void deleteGender(int codGender) throws SQLException;
    void updateGender(GenderDto gender) throws SQLException;
    GenderDto getGenderById(int codGender) throws SQLException;
}
