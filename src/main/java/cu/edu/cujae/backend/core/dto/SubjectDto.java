package cu.edu.cujae.backend.core.dto;

//Asignaturas
public class SubjectDto {
    private int codSubject;
    private int hours;
    private String subject;
    private int codYear;

    public SubjectDto() {
    }

    public SubjectDto(int codSubject, int hours, String subject, int codYear) {
        this.codSubject = codSubject;
        this.hours = hours;
        this.subject = subject;
        this.codYear = codYear;
    }

    public int getCodSubject() {
        return codSubject;
    }

    public void setCodSubject(int codSubject) {
        this.codSubject = codSubject;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCodYear() {
        return codYear;
    }

    public void setCodYear(int codYear) {
        this.codYear = codYear;
    }

}
