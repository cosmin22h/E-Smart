package ro.tuc.ds2021.handaric.cosmin.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.CustomException;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.DuplicateResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.NotFoundResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers.UserMapper;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.CredentialsDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.LoginTokenDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.LogoutDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.RegisterUserDTO;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Admin;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.AppUser;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.AuthSession;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.AdminRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.AppUserRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.AuthSessionRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.ClientRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.services.AuthService;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {
    private static final String BAD_CREDENTIALS_MESSAGE = "Your email or password was incorrect. Please try again.";
    private static final String LOGOUT_MESSAGE = "Logout successful!";

    private final AppUserRepository appUserRepository;
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final AuthSessionRepository authSessionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AppUserRepository appUserRepository, AdminRepository adminRepository, ClientRepository clientRepository, AuthSessionRepository authSessionRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.authSessionRepository = authSessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UUID register(RegisterUserDTO newUser) throws CustomException {
        // CHECK USERNAME
        DuplicateResourceException customException = this.checkUsernameIsUnique(newUser.getUsername());
        if (customException != null) {
            throw customException;
        }
        // CHECK EMAIL
        customException = this.checkEmailIsUnique(newUser.getEmail());
        if (customException != null) {
            throw customException;
        }
        AppUser user = UserMapper.mapRegisterUserDtoToUserModel(newUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UUID idUserInserted = null;
        if (user instanceof Admin) {
            Admin admin = adminRepository.save((Admin) user);
            idUserInserted = admin.getId();
        } else if (user instanceof Client) {
            Client client = clientRepository.save((Client) user);
            idUserInserted = client.getId();
        }

        return idUserInserted;
    }

    @Override
    @Transactional
    public LoginTokenDTO login(CredentialsDTO credentials) throws CustomException {
        AppUser user = appUserRepository.findAppUserByUsername(credentials.getUsername());

        if (user == null) {
            throw this.badCredentials();
        }

        if (passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            AuthSession authSession = AuthSession.builder()
                    .user(user)
                    .loginTime(new Date())
                    .build();
            AuthSession authSessionSaved = authSessionRepository.save(authSession);

            return LoginTokenDTO.builder()
                    .id(user.getId())
                    .role(user.getClass().getSimpleName().toUpperCase())
                    .idSession(authSessionSaved.getId())
                    .build();
            
        }

        throw this.badCredentials();
    }

    @Override
    @Transactional
    public LogoutDTO logout(LoginTokenDTO token) throws CustomException {
        AppUser user = appUserRepository.findById(token.getId()).orElse(null);
        if (user == null) {
            String message = "User not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.user.cant-find");
            errors.add("error.user.logout");
            throw new NotFoundResourceException(message, errors);
        }

        AuthSession authSession = authSessionRepository.findById(token.getIdSession()).orElse(null);
        if (authSession == null) {
            String message = "Login session not found!";
            List<String> errors = new ArrayList<>();
            errors.add("error.auth-session.cant-find");
            errors.add("error.auth-session.logout");
            throw new NotFoundResourceException(message, errors);
        }

        authSession.setLogoutTime(new Date());
        AuthSession authSessionEnded = authSessionRepository.save(authSession);

        return LogoutDTO.builder()
                .message(LOGOUT_MESSAGE)
                .logoutTime(authSessionEnded.getLogoutTime())
                .build();
    }

    private DuplicateResourceException checkUsernameIsUnique(String username) {
        if (appUserRepository.findAppUserByUsername(username) != null) {
            String message = "This username is already used!";
            List<String> errors = new ArrayList<>();
            errors.add("error.user.cant-save-user");
            errors.add("error.user.username-taken");
            return new DuplicateResourceException(message, errors);
        }

        return null;
    }

    private DuplicateResourceException checkEmailIsUnique(String email) {
        if (appUserRepository.findAppUserByEmail(email) != null) {
            String message = "This email is already used!";
            List<String> errors = new ArrayList<>();
            errors.add("error.user.cant-save-user");
            errors.add("error.user.email-taken");
            return new DuplicateResourceException(message, errors);
        }

        return null;
    }

    private NotFoundResourceException badCredentials() {
        List<String> errors = new ArrayList<>();
        errors.add("error.user.login");
        errors.add("error.user.bad-credentials");

        return new NotFoundResourceException(BAD_CREDENTIALS_MESSAGE, errors);
    }
}
