package cu.edu.cujae.backend.service;


import cu.edu.cujae.backend.core.dto.SubjectDto;
import cu.edu.cujae.backend.core.dto.YearDto;
import cu.edu.cujae.backend.core.service.SubjectService;
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
public class SubjectServiceImp implements SubjectService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void createSubject(SubjectDto subject) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("create_asignatura(?,?,?)");
            cs.setString(1,subject.getNameSubject());
            cs.setInt(2, subject.getHours());
            cs.setInt(3, subject.getYear().getCodYear());
        }
    }

    @Override
    public List<SubjectDto> getSubjects() throws SQLException {
        List<SubjectDto> subjectList = new ArrayList<SubjectDto>();
        ResultSet rs = jdbcTemplate.getDataSource().getConnection().createStatement().
                executeQuery("SELECT * from asignatura");
        while (rs.next()){
         subjectList.add(new SubjectDto(
            rs.getInt("cod_asignatura"),
            rs.getInt("horas"),
            rs.getString("nombre"),
            new YearDto(rs.getInt("cod_anno"))
         ));
        }
        return subjectList;
    }

    @Override
    public void deleteSubject(int codSubject) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("delete_asignatura(?)");
            cs.setInt(1, codSubject);
        }
    }

    @Override
    public void updateSubject(SubjectDto subject) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("update_asignatura(?,?,?,?)");
            cs.setInt(1, subject.getCodSubject());
            cs.setString(2, subject.getNameSubject());
            cs.setInt(3, subject.getHours());
            cs.setInt(4, subject.getYear().getCodYear());
        }
    }
}