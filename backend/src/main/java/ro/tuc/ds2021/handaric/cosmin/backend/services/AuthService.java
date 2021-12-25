package ro.tuc.ds2021.handaric.cosmin.backend.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.CredentialsDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.LoginTokenDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.LogoutDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.RegisterUserDTO;

import java.util.UUID;

@Component
public interface AuthService {
    UUID register(RegisterUserDTO newUser);
    LoginTokenDTO login(CredentialsDTO credentials);
    LogoutDTO logout(LoginTokenDTO token);
}
