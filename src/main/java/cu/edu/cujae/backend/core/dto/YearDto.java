package cu.edu.cujae.backend.core.dto;

public class YearDto {

    private int codYear;
    private int codCurso;
    private int year;

    public YearDto() {
    }

    public YearDto(int codYear, int codCurso, int year) {
        this.codYear = codYear;
        this.codCurso = codCurso;
        this.year = year;
    }

    public int getCodYear() {
        return codYear;
    }

    public void setCodYear(int codYear) {
        this.codYear = codYear;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
