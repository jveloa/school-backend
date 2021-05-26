package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.MunicipalityDto;

import java.sql.SQLException;
import java.util.List;

public interface MunicipalityService {
    void createMunicipality(MunicipalityDto municipality) throws SQLException;
    List<MunicipalityDto> getMunicipalitys() throws SQLException;
    void deleteMunicipality(int codMunicipality) throws SQLException;
    void updateMunicipality(MunicipalityDto municipality) throws SQLException;
}
