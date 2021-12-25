package ro.tuc.ds2021.handaric.cosmin.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.DuplicateResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.NotFoundResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.CredentialsDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.LoginTokenDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.LogoutDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.RegisterUserDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.services.AuthService;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UUID> registerUser(@RequestBody RegisterUserDTO user) throws DuplicateResourceException {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginTokenDTO> loginUser(@RequestHeader String username, @RequestHeader String password) throws NotFoundResourceException {
        CredentialsDTO credentials = CredentialsDTO.builder()
                .username(username)
                .password(password)
                .build();
        return ResponseEntity.ok(authService.login(credentials));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutDTO> logoutUser(@RequestHeader UUID id, @RequestHeader String role, @RequestHeader(required = false) UUID idSession) throws NotFoundResourceException {
        LoginTokenDTO token = LoginTokenDTO.builder()
                .id(id)
                .role(role)
                .idSession(idSession)
                .build();
        return ResponseEntity.ok(authService.logout(token));
    }

}
