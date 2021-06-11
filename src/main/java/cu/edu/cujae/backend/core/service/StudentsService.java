package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.MunicipalityDto;
import cu.edu.cujae.backend.core.dto.StudentDto;


import java.sql.SQLException;
import java.util.List;

public interface StudentsService {
    void createStudents(StudentDto student) throws SQLException;
    List<StudentDto> getStudents() throws SQLException;
    void deleteStudents(int codStudents) throws SQLException;
    void updateStudents(StudentDto student) throws SQLException;
    StudentDto getStudentById(int codStudent) throws SQLException;

}
