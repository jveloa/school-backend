package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.GroupDto;
import cu.edu.cujae.backend.core.dto.RecordDto;
import cu.edu.cujae.backend.core.dto.StudentDto;
import cu.edu.cujae.backend.core.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RecordDto> getRecord() throws SQLException {
        List<RecordDto> recordList = new ArrayList<RecordDto>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from registro");
            while (rs.next()) {
                recordList.add(new RecordDto(
                    new GroupDto(rs.getInt("cod_grupo")),
                    new StudentDto(rs.getInt("cod_estudiante"))
                ));
            }
        }
        return recordList;
    }

    @Override
    public void createRecord(RecordDto record) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = con.prepareCall("{call create_registro(?,?)}");
            cs.setInt(1, record.getStudent().getCodStudent());
            cs.setInt(2, record.getGroup().getCodGroup());
            cs.executeUpdate();
        }
    }

    @Override
    public void updateRecord(RecordDto record) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement cs = con.prepareStatement("update registro set cod_grupo = ? where cod_estudiante = ? ");
            cs.setInt(1, record.getGroup().getCodGroup());
            cs.setInt(2, record.getStudent().getCodStudent());
            cs.executeQuery();
        }
    }

    @Override
    public void deleteRecord(int codRecord) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = con.prepareCall("{call delete_registro(?)}");
            cs.setInt(1,codRecord);
            cs.executeUpdate();
        }
    }
}

