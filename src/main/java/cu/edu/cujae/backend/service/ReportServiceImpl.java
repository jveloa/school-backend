package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.reportDto.StudentsByGroupDto;
import cu.edu.cujae.backend.core.dto.reportDto.SubjectsByYearDto;
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
    public List<StudentsByGroupDto> getStudentsbyGroup() throws SQLException {
        List<StudentsByGroupDto> studentForGroupList = new ArrayList<>();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            con.setAutoCommit(false);
            CallableStatement cs = con.prepareCall("{call reporte_estudiantes_por_grupo(?)}");
            cs.setNull(1, Types.REF,"refcursor");
            cs.registerOutParameter(1,Types.REF_CURSOR);
            cs.execute();
            ResultSet re = (ResultSet) cs.getObject(1);
            while (re.next()){
                studentForGroupList.add( new StudentsByGroupDto(
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

    @Override
    public List<SubjectsByYearDto> getSubjectsByYear() throws SQLException {
        List<SubjectsByYearDto> subjectsByYearList = new ArrayList<>();
        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            con.setAutoCommit(false);
            CallableStatement cs = con.prepareCall("{call reporte_asignaturas_impartidas(?)}");
            cs.setNull(1,Types.REF,"refcursor");
            cs.registerOutParameter(1,Types.REF_CURSOR);
            cs.execute();
            ResultSet re = (ResultSet) cs.getObject(1);
            while(re.next()){
                subjectsByYearList.add(new SubjectsByYearDto(
                   re.getString("curso"),
                   re.getInt("anno"),
                   re.getString("nombre"),
                   re.getInt("horas")
                ));
            }

        }
        return subjectsByYearList;
    }
}
