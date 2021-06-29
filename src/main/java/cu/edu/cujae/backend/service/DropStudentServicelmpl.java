package cu.edu.cujae.backend.service;


import cu.edu.cujae.backend.core.dto.DropStudentDto;
import cu.edu.cujae.backend.core.dto.ReasonDropDto;
import cu.edu.cujae.backend.core.dto.StudentDto;
import cu.edu.cujae.backend.core.service.DropStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class DropStudentServicelmpl implements DropStudentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void createDropStudent(DropStudentDto dropStudent) throws SQLException {

        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = conn.prepareCall(
                    "{call create_baja(?,?)}");
            cs.setInt(1,dropStudent.getStudent().getCodStudent());
            cs.setInt(2,dropStudent.getReasonDrop().getCodReason());
            cs.executeUpdate();
        }
    }

    @Override
    public List<DropStudentDto>getDropStudent() throws SQLException {
        List<DropStudentDto> dropStudentList = new ArrayList<DropStudentDto>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()){
            ResultSet rs = con.createStatement().executeQuery("SELECT * from baja");
            while (rs.next()){
                dropStudentList.add(new DropStudentDto(new ReasonDropDto(rs.getInt("cod_motivo"),null),new StudentDto(rs.getInt("cod_estudiante"),null,null,null,null)
                ));
            }
            return dropStudentList;
        }

    }

    @Override
    public void deleteDropStudent(int cod_Student) throws SQLException {
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = conn.prepareStatement("delete from baja where cod_estudiante = ?");
            ps.setInt(1,cod_Student);

            ps.executeUpdate();
        }
    }

    @Override
    public void updateDropStudent(DropStudentDto dropStudentDto) throws SQLException {
        try(Connection conn = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = conn.prepareCall("update_baja(?,?)");
            cs.setInt(1,dropStudentDto.getStudent().getCodStudent());
            cs.setInt(2, dropStudentDto.getReasonDrop().getCodReason());
        }
    }
}

