package cu.edu.cujae.backend.core.dto;

public class UserDto {
    private int codUser;
    private String username;
    private String password;
    private int codRole;

    public UserDto() {
    }

    public UserDto(int codUser, String username, String password, int codRole) {
        this.codUser = codUser;
        this.username = username;
        this.password = password;
        this.codRole = codRole;
    }

    public int getCodUser() {
        return codUser;
    }

    public void setCodUser(int codUser) {
        this.codUser = codUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCodRole() {
        return codRole;
    }

    public void setCodRole(int codRole) {
        this.codRole = codRole;
    }
}

