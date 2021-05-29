package cu.edu.cujae.backend.api.controller;


import cu.edu.cujae.backend.core.dto.GroupDto;
import cu.edu.cujae.backend.core.service.GroupService;
import cu.edu.cujae.backend.service.GenderServiceImpl;
import cu.edu.cujae.backend.service.GroupServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/")
    public ResponseEntity<List<GroupDto>> getGroup() throws SQLException {
        List<GroupDto> groupList = groupService.getGroupList();
        return ResponseEntity.ok(groupList);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody GroupDto group) throws SQLException{
        groupService.createGroup(group);
        return ResponseEntity.ok("Grupo insertado");
    }

    @DeleteMapping("/{codGroup}")
    public ResponseEntity<String> delete(@PathVariable int codGroup) throws SQLException{
        groupService.deleteGroup(codGroup);
        return ResponseEntity.ok("Grupo eliminado");
    }

}
















