package cu.edu.cujae.backend.core.dto;

public class StudentDto {
    private int codStudent;
    private String name;
    private String lastName;
    private int codGender;
    private int codMunicipality;

    public StudentDto() {
    }

    public StudentDto(int codStudent, String name, String lastName, int codGender, int codMunicipality) {
        this.codStudent = codStudent;
        this.name = name;
        this.lastName = lastName;
        this.codGender = codGender;
        this.codMunicipality = codMunicipality;
    }

    public int getCodStudent() {
        return codStudent;
    }

    public void setCodStudent(int codStudent) {
        this.codStudent = codStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCodGender() {
        return codGender;
    }

    public void setCodGender(int codGender) {
        this.codGender = codGender;
    }

    public int getCodMunicipality() {
        return codMunicipality;
    }

    public void setCodMunicipality(int codMunicipality) {
        this.codMunicipality = codMunicipality;
    }
}
