package cu.edu.cujae.backend.core.dto;

public class GroupDto {
    private int codGroup;
    private int codYear;
    private int numberGroup;

    public GroupDto() {
    }

    public GroupDto(int codGroup, int codYear, int numberGroup) {
        this.codGroup = codGroup;
        this.codYear = codYear;
        this.numberGroup = numberGroup;
    }

    public int getCodGroup() {
        return codGroup;
    }

    public void setCodGroup(int codGroup) {
        this.codGroup = codGroup;
    }

    public int getCodYear() {
        return codYear;
    }

    public void setCodYear(int codYear) {
        this.codYear = codYear;
    }

    public int getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
    }
}
