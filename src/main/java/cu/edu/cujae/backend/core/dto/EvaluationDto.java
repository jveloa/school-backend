package cu.edu.cujae.backend.core.dto;

public class EvaluationDto {
    private int codSubject;
    private int codStudent;
    private int codYear;
    private int codEvaluation;

    public EvaluationDto() {
    }

    public EvaluationDto(int codSubject, int codStudent, int codYear, int codEvaluation) {
        this.codSubject = codSubject;
        this.codStudent = codStudent;
        this.codYear = codYear;
        this.codEvaluation = codEvaluation;
    }

    public int getCodSubject() {
        return codSubject;
    }

    public void setCodSubject(int codSubject) {
        this.codSubject = codSubject;
    }

    public int getCodStudent() {
        return codStudent;
    }

    public void setCodStudent(int codStudent) {
        this.codStudent = codStudent;
    }

    public int getCodYear() {
        return codYear;
    }

    public void setCodYear(int codYear) {
        this.codYear = codYear;
    }

    public int getCodEvaluation() {
        return codEvaluation;
    }

    public void setCodEvaluation(int codEvaluation) {
        this.codEvaluation = codEvaluation;
    }
}
