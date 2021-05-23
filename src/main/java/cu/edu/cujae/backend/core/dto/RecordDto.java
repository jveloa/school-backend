package cu.edu.cujae.backend.core.dto;

public class RecordDto {
    private int codGroup;
    private int codStudent;

    public RecordDto() {
    }

    public RecordDto(int codGroup, int codStudent) {
        this.codGroup = codGroup;
        this.codStudent = codStudent;
    }

    public int getCodGroup() {
        return codGroup;
    }

    public void setCodGroup(int codGroup) {
        this.codGroup = codGroup;
    }

    public int getCodStudent() {
        return codStudent;
    }

    public void setCodStudent(int codStudent) {
        this.codStudent = codStudent;
    }
}
