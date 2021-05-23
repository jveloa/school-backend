package cu.edu.cujae.backend.core.dto;

public class RoleDto {
    private int codRole;
    private int role;

    public RoleDto() {
    }

    public RoleDto(int codRole, int role) {
        this.codRole = codRole;
        this.role = role;
    }

    public int getCodRole() {
        return codRole;
    }

    public void setCodRole(int codRole) {
        this.codRole = codRole;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
