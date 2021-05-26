package cu.edu.cujae.backend.core.dto;

public class YearDto {

    private int codYear;
    private int codCourse;
    private int year;

    public YearDto() {
    }

    public YearDto(int codYear, int codCourse, int year) {
        this.codYear = codYear;
        this.codCourse = codCourse;
        this.year = year;
    }

    public int getCodYear() {
        return codYear;
    }

    public void setCodYear(int codYear) {
        this.codYear = codYear;
    }

    public int getCodCourse() {
        return codCourse;
    }

    public void setCodCourse(int codCourse) {
        this.codCourse = codCourse;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
