package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.reportDto.StudentForGroupDto;
import cu.edu.cujae.backend.core.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<StudentForGroupDto> getStudentForGroup() throws SQLException {
        List<StudentForGroupDto> studentForGroupList = new ArrayList<>();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            con.setAutoCommit(false);
            CallableStatement cs = con.prepareCall("{call reporte_estudiantes_por_grupo(?)}");
            cs.setNull(1, Types.REF,"refcursor");
            cs.registerOutParameter(1,Types.REF_CURSOR);
            cs.execute();
            ResultSet re = (ResultSet) cs.getObject(1);
            while (re.next()){
                studentForGroupList.add( new StudentForGroupDto(
                    re.getString("curso"),
                    re.getInt("anno"),
                    re.getString("nombre"),
                    re.getString("apellidos"),
                    re.getInt("numero"),
                    re.getInt("cod_estudiante")
                ));
            }

        }
        return studentForGroupList;
    }
}
