package cu.edu.cujae.backend.api.controller;

import java.sql.SQLException;
import java.util.List;

import cu.edu.cujae.backend.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cu.edu.cujae.backend.core.dto.UserDto;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    @Autowired
    private UserService userService;

	@GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers() throws SQLException {
        List<UserDto> userList = userService.getUsers();
        return ResponseEntity.ok(userList);
    }

	@PostMapping("/")
    public ResponseEntity<String> create(@RequestBody UserDto user) throws SQLException {
	    userService.createUser(user);
        return ResponseEntity.ok("Usuario insertado");
    }

	@PutMapping("/")
    public ResponseEntity<String> update(@RequestBody UserDto user) throws SQLException {
        userService.updateUser(user);
        return ResponseEntity.ok("Usuario Modificado");
    }

	@DeleteMapping("/{codUser}")
    public ResponseEntity<String> delete(@PathVariable int codUser) throws SQLException {
	    userService.deleteUsers(codUser);
        return ResponseEntity.ok("Usuario Eliminado");
    }
}
