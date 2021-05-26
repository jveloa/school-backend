package cu.edu.cujae.backend.core.dto;

public class YearDto {

    private int codYear;
    private CourseDto course;
    private int yearNumber;

    public YearDto() {
    }

    public YearDto(int codYear, CourseDto course, int yearNumber) {
        this.codYear = codYear;
        this.course = course;
        this.yearNumber = yearNumber;
    }

    public YearDto(int codYear) {
        this.codYear = codYear;
    }

    public int getCodYear() {
        return codYear;
    }

    public void setCodYear(int codYear) {
        this.codYear = codYear;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }
}
