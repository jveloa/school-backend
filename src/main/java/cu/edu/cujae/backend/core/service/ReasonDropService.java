package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.ReasonDropDto;

import java.sql.SQLException;
import java.util.List;

public interface ReasonDropService {
    void createReasonDrop(ReasonDropDto reasonDrop) throws SQLException;
    List<ReasonDropDto> getReasonsDrop() throws SQLException;
    void deleteReasonDrop(int codReasonDrop) throws SQLException;
    void updateReasonDrop(ReasonDropDto reasonDrop) throws SQLException;
}
