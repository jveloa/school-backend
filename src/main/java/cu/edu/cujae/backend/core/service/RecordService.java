package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.RecordDto;

import java.sql.SQLException;
import java.util.List;

public interface RecordService {
    List<RecordDto> getRecord() throws SQLException;
    void createRecord(RecordDto record) throws SQLException;
    void updateRecord(RecordDto record) throws SQLException;
    void deleteRecord(int codRecord) throws SQLException;
}
