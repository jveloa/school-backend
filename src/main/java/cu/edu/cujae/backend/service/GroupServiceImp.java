package cu.edu.cujae.backend.service;

import cu.edu.cujae.backend.core.dto.*;
import cu.edu.cujae.backend.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImp implements GroupService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private YearService yearService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private EvaluationService evaluationService;
    @Autowired
    private RecordService recordService;

    @Override
    public List<GroupDto> getGroupList() throws SQLException {
        List<GroupDto> groupList = new ArrayList<GroupDto>();
        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("select * from grupo");
            while (rs.next()) {
                groupList.add(new GroupDto(
                    rs.getInt("cod_grupo"),
                    new YearDto(rs.getInt("cod_anno"),0,new CourseDto()),
                    rs.getInt("numero")
                ));
            }

        }
        setYearData(groupList);
        return groupList;
    }

    @Override
    public void createGroup(GroupDto group) throws SQLException {
       try (Connection con = jdbcTemplate.getDataSource().getConnection()){
            ResultSet rs = con.createStatement().executeQuery(
                    "Select cod_anno from anno where cod_curso = "+ group.getYear().getCourse().getCodCourse() +
                            "and anno =" + group.getYear().getYearNumber());
            rs.next();
            CallableStatement cs = con.prepareCall("{call create_grupo(?,?)}");
            cs.setInt(1,group.getNumberGroup());
            cs.setInt(2,rs.getInt("cod_anno"));
            cs.executeUpdate();

        }
    }

    @Override
    public void deleteGroup(int codGroup) throws SQLException {
        try (Connection con = jdbcTemplate.getDataSource().getConnection()){
            CallableStatement cs = con.prepareCall("{call delete_grupo(?)}");
            cs.setInt(1,codGroup);
            cs.executeUpdate();
        }
    }

    @Override
    public void createNewCourseGroups(int codNewCourse, int codLastCourse, int years) throws SQLException {
        int i;
        for (i=0; i<years; i++){
            if(i==0){
               List<StudentDto> disapproveds = evaluationService.disapprovedsForYear(codLastCourse, i+1);
               int groupsAmount = Math.round(disapproveds.size()/1);
               for (int j = 0; j< groupsAmount; j++){
                   String groupName = String.valueOf(i+1) + String.valueOf(j+1);
                   int codYear = yearService.getCodAnnoByCourse(i+1, codNewCourse);
                   createGroup(new GroupDto(0, new YearDto(codYear), Integer.valueOf(groupName)));
               }
            }
            else{
                List<StudentDto> approveds = evaluationService.approvedsForYear(codLastCourse, i);
                List<StudentDto> disapproveds = evaluationService.disapprovedsForYear(codLastCourse, i+1);
                int groupsAmount = Math.round((disapproveds.size() +approveds.size())/1)  ;
                for (int j = 0; j< groupsAmount; j++){
                    String groupName = String.valueOf(i+1) + String.valueOf(j+1);
                    int codYear = yearService.getCodAnnoByCourse(i+1, codLastCourse);
                    createGroup(new GroupDto(0, new YearDto(codYear,i+1, new CourseDto(codNewCourse)), Integer.valueOf(groupName)));
                }
            }
        }
    }

    @Override
    public void fillGroupsWithStudents(int years, int codCurrentCourse, int codLastCourse) throws SQLException {
        for(int i =0; i<years;i++){
            if (i==0){
                List<StudentDto> disapproveds = evaluationService.disapprovedsForYear(codLastCourse, i+1);
                int groupAmount = Math.round(disapproveds.size()/5);
                List<GroupDto> groups= getGroupsByCourseAndYear(codCurrentCourse, i+1);
                int cont = 0;
                for (StudentDto student: disapproveds) {
                    if (cont >= groups.size()){
                        cont = 0;
                        recordService.createRecord(new RecordDto(
                               groups.get(cont),
                                student
                        ));
                        cont= cont + 1;

                    }
                    else{
                        recordService.createRecord(new RecordDto(
                                groups.get(cont),
                                student
                        ));
                        cont= cont + 1;
                    }
                }
            }
            else {
                List<StudentDto> approveds = evaluationService.approvedsForYear(codLastCourse, i);
                List<StudentDto> disapproveds = evaluationService.disapprovedsForYear(codLastCourse, i+1);
                List<StudentDto> total = new ArrayList<StudentDto>(approveds);
                total.addAll(disapproveds);
                int groupsAmount = Math.round((approveds.size()+disapproveds.size())/5);
                List<GroupDto> groups= getGroupsByCourseAndYear(codCurrentCourse, i+1);
                int cont = 0;
                for (StudentDto student: total) {
                    if (cont >= groups.size()){
                        cont = 0;
                        recordService.createRecord(new RecordDto(
                                groups.get(cont),
                                student
                        ));
                        cont= cont + 1;

                    }
                    else{
                        recordService.createRecord(new RecordDto(
                                groups.get(cont),
                                student
                        ));
                        cont= cont + 1;
                    }
                }
            }
        }
    }

    @Override
    public List<GroupDto> getGroupsByCourseAndYear(int codCourse, int year) throws SQLException {
        List<GroupDto> groups = new ArrayList<GroupDto>();
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            conn.setAutoCommit(false);
            CallableStatement cs = conn.prepareCall("{call grupos_por_curso_y_anno(?, ?, ?)}");
            cs.setNull(1, Types.REF, "refcursor");
            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.setInt(2, codCourse);
            cs.setInt(3, year);
            cs.execute();
            ResultSet re = (ResultSet) cs.getObject(1);

            while (re.next()) {
                groups.add(new GroupDto(
                        re.getInt("cod_grupo")
                        , new YearDto(re.getInt("cod_anno"))
                        , re.getInt("numero")

                ));
            }
            return groups;
        }
    }

    private void setYearObject(List<GroupDto> groups) throws SQLException {
       int i=0;
        while ( i < groups.size()) {
            List<YearDto>years=yearService.getYears();
            for (int j=0;j<years.size();j++){
            if(groups.get(i).getYear().getCodYear()==years.get(j).getCodYear()){
                groups.get(i).getYear().setYearNumber(years.get(j).getYearNumber());
                groups.get(i).getYear().getCourse().setCodCourse(years.get(j).getCourse().getCodCourse());
                groups.get(i).getYear().getCourse().setCourse(years.get(j).getCourse().getCourse());
            }
            }
             i++;
        }
    }

    private void setYearData(List< GroupDto > groups) throws SQLException {
            try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.
                        executeQuery("SELECT * from anno");
                Map<Integer, String> mapCourses = courseService.getCoursesMap();
                for (GroupDto group : groups) {
                    yearService.setData(group.getYear(), mapCourses, rs);
                }
            }
    }
}



















