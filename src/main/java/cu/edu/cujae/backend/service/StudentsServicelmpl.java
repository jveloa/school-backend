package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.*;

import cu.edu.cujae.backend.core.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StudentsServicelmpl implements StudentsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MunicipalityServicelmpl municipalityService;

    @Autowired
    private GenderServiceImpl genderService;

    @Override
    public List<StudentDto> getStudents() throws SQLException {
        List<StudentDto> students = new ArrayList<>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM estudiante ORDER BY cod_estudiante");
            while (rs.next()) {
                students.add(new StudentDto(
                        rs.getInt("cod_estudiante"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        new GenderDto(rs.getInt("cod_sexo"),""),
                        new MunicipalityDto(rs.getInt("cod_municipio"),"municipio")
                ));
            }
            students=llenarGender(students);
            setMunicipalityNames(students);
            return students;
        }
    }

    @Override
    public void createStudents(StudentDto students) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = con.prepareCall("{call create_estudiante(?,?,?,?)}");
            cs.setString(1, students.getName());
            cs.setString(2, students.getLastName());
            cs.setInt(4, students.getGender().getCodGender());
            cs.setInt(3, students.getMunicipality().getCodMunicipality());
            cs.executeUpdate();
        }
    }

    @Override
    public void updateStudents(StudentDto students) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = con.prepareCall("{call update_estudiante(?, ?, ?, ?,?)}");
            cs.setInt(1, students.getCodStudent());
            cs.setString(2, students.getName());
            cs.setString(3, students.getLastName());
            cs.setInt(4, students.getMunicipality().getCodMunicipality());
            cs.setInt(5, students.getGender().getCodGender());

            cs.executeUpdate();
        }
    }


    @Override
    public void deleteStudents(int codStudents) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            CallableStatement cs = con.prepareCall( "{call delete_estudiante(?)}");
            cs.setInt(1, codStudents);
            cs.executeUpdate();
        }
    }

    @Override
    public StudentDto getStudentById(int codStudent) throws SQLException {
        StudentDto student = null;

        try(Connection con = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM estudiante where cod_estudiante = ? ");
            ps.setInt(1,codStudent);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                student = new StudentDto(
                        rs.getInt("cod_estudiante"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        genderService.getGenderById(rs.getInt("cod_sexo")),
                        municipalityService.getMunicipalityById(rs.getInt("cod_municipio"))

                );
            }
        }

        return student;
    }

     public List<StudentDto> llenarGender(List<StudentDto> students)throws SQLException{
           int i=0;
        while (i<students.size()){
            if(students.get(i).getGender().getCodGender()==1){
                students.get(i).getGender().setGender("Masculino");
            }else{
                students.get(i).getGender().setGender("Femenino");
            }
            i++;
        }
        return students;
     }
     private void setMunicipalityName(StudentDto student, Map<Integer,String> mapMunicipality) {
        student.getMunicipality().setMunicipality(mapMunicipality.get(student.getMunicipality().getCodMunicipality()));
     }

     private void setMunicipalityNames(List<StudentDto> students) throws SQLException {
        Map<Integer,String> mapMunicipality = municipalityService.getMunicipalityMap();
        for (StudentDto student: students) {
            student.getMunicipality().setMunicipality( mapMunicipality.get(student.getMunicipality().getCodMunicipality()) );
        }
     }
}
