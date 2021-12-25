package ro.tuc.ds2021.handaric.cosmin.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.*;
import ro.tuc.ds2021.handaric.cosmin.backend.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // FETCH
    @GetMapping()
    public ResponseEntity<List<UserDTO>> fetchUsers(@RequestParam(required = false) Integer noOfPage, @RequestParam(required = false) Integer itemsPerPage) {
        return ResponseEntity.ok(userService.fetchUsers(noOfPage, itemsPerPage));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> fetchClients(@RequestParam(required = false) Integer noOfPage, @RequestParam(required = false) Integer itemsPerPage) {
        return ResponseEntity.ok(userService.fetchClients(noOfPage, itemsPerPage));
    }

    @GetMapping("/admin")
    public ResponseEntity<UserDTO> fetchAdmin(@RequestParam UUID id) {
        return ResponseEntity.ok(userService.fetchAdmin(id));
    }

    @GetMapping("/client")
    public ResponseEntity<ClientDTO> fetchClient(@RequestParam UUID id) {
        return ResponseEntity.ok(userService.fetchClient(id));
    }

    // EDIT
    @PutMapping("/update")
    public ResponseEntity<UUID> updateAdmin(@RequestBody UserDTO admin) {
        return ResponseEntity.ok(userService.updateAdmin(admin));
    }

    @PutMapping("/client/update")
    public ResponseEntity<UUID> updateClient(@RequestBody ClientDTO client) {
        return ResponseEntity.ok(userService.updateClient(client));
    }

    @PutMapping("/change-password")
    public ResponseEntity<UUID> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return ResponseEntity.ok(userService.changePassword(changePasswordDTO));
    }

    // DELETE
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }






}
