package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.MunicipalityDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MunicipalityService {
    Map<Integer, String> getMunicipalityMap()throws SQLException;
    void createMunicipality(MunicipalityDto municipality) throws SQLException;
    List<MunicipalityDto> getMunicipalities() throws SQLException;
    void deleteMunicipality(int codMunicipality) throws SQLException;
    void updateMunicipality(MunicipalityDto municipality) throws SQLException;
    MunicipalityDto getMunicipalityById(int codMunicipality) throws SQLException;

}
