package cu.edu.cujae.backend.core.service;

import cu.edu.cujae.backend.core.dto.DropStudentDto;


import java.sql.SQLException;
import java.util.List;

public interface DropStudentService {
    void createDropStudent(DropStudentDto dropStudent) throws SQLException;
    List<DropStudentDto> getDropStudent() throws SQLException;
    void deleteDropStudent( int cod_student) throws SQLException;
    void updateDropStudent(DropStudentDto dropStudent) throws SQLException;
}
