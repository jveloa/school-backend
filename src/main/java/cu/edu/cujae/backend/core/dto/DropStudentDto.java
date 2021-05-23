package cu.edu.cujae.backend.core.dto;


//Baja
public class DropStudentDto {
    private int codDrop;
    private int codStudent;

    public DropStudentDto() {
    }

    public DropStudentDto(int codDrop, int codStudent) {
        this.codDrop = codDrop;
        this.codStudent = codStudent;
    }

    public int getCodDrop() {
        return codDrop;
    }

    public void setCodDrop(int codDrop) {
        this.codDrop = codDrop;
    }

    public int getCodStudent() {
        return codStudent;
    }

    public void setCodStudent(int codStudent) {
        this.codStudent = codStudent;
    }
}
