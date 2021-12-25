package ro.tuc.ds2021.handaric.cosmin.backend.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuc.ds2021.handaric.cosmin.backend.controllers.handlers.exceptions.NotFoundResourceException;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.*;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers.UserMapper;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Admin;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.AppUser;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.AuthSession;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.Client;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.AppUserRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.ClientRepository;
import ro.tuc.ds2021.handaric.cosmin.backend.services.UserService;
import ro.tuc.ds2021.handaric.cosmin.backend.utils.Utils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<UserDTO> fetchUsers(Integer noOfPage, Integer itemsPerPage) {
        List<AppUser> users = fetchUsersByPage(noOfPage, itemsPerPage);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (AppUser user: users) {
            AuthSession lastAuthSession = Utils.getLastSession(user.getAuthSessions());
            userDTOS.add(UserMapper.mapUserModelToDtoWithLastSession(user, lastAuthSession));
        }

        return userDTOS;
    }

    @Override
    @Transactional
    public List<ClientDTO> fetchClients(Integer noOfPage, Integer itemsPerPage) {
        List<Client> clients = fetchClientsByPage(noOfPage, itemsPerPage);
        List<ClientDTO> clientDTOS = new ArrayList<>();
        for (Client client: clients) {
            AuthSession lastAuthSession = Utils.getLastSession(client.getAuthSessions());
            clientDTOS.add(UserMapper.mapClientModelToDtoWithLastSession(client, lastAuthSession));
        }
        return clientDTOS;
    }

    @Override
    @Transactional
    public UserDTO fetchAdmin(UUID id) {
        AppUser user = appUserRepository.findById(id).orElseThrow(this::userNotFound);
        return UserMapper.mapUserModelToDto(user);
    }

    @Override
    @Transactional
    public ClientDTO fetchClient(UUID id) {
        Client client = clientRepository.findById(id).orElseThrow(this::userNotFound);
        return UserMapper.mapClientModelToDto(client);
    }

    @Override
    @Transactional
    public UUID updateAdmin(UserDTO adminDTO) {
        Admin admin = (Admin) appUserRepository.findById(adminDTO.getId()).orElseThrow(this::userNotFound);
        admin.setUsername(adminDTO.getUsername());
        admin.setEmail(adminDTO.getEmail());
        return appUserRepository.save(admin).getId();
    }

    @Override
    @Transactional
    public UUID updateClient(ClientDTO clientDTO) {
        Client client = clientRepository.findById(clientDTO.getId()).orElseThrow(this::userNotFound);
        client.setUsername(clientDTO.getUsername());
        client.setEmail(clientDTO.getEmail());
        client.setFirsName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setDisabled(clientDTO.isDisabled());
        client.setBirthday(clientDTO.getBirthday());
        client.setAddress(UserMapper.mapAddressDtoToModel(clientDTO.getAddress()));
        return clientRepository.save(client).getId();
    }

    @Override
    @Transactional
    public UUID changePassword(ChangePasswordDTO changePasswordDTO) {
        AppUser user = appUserRepository.findById(changePasswordDTO.getIdUserToChange()).orElseThrow(this::userNotFound);
        if (changePasswordDTO.getIsAdmin() ||
                passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            AppUser userSaved = appUserRepository.save(user);

            return userSaved.getId();
        } else {
            String message = "Incorrect password!";
            List<String> errors = new ArrayList<>();
            errors.add("error.user.change-password");
            errors.add("error.user.old-password-incorrect");

            throw new NotFoundResourceException(message, errors);
        }
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        AppUser userToDelete = appUserRepository.findById(id).orElseThrow(this::userNotFound);
        appUserRepository.delete(userToDelete);
    }

    private List<AppUser> fetchUsersByPage(Integer noOfPage, Integer itemsPerPage) {
        List<AppUser> users;

        if (itemsPerPage == null) {
            users = new ArrayList<>(appUserRepository.findAll());
        } else if (noOfPage == null || noOfPage <= 1) {
            users = appUserRepository.findAllForFirstPage(itemsPerPage);
        } else {
            int offset = Utils.computeOffset(noOfPage, itemsPerPage);
            users = appUserRepository.findAllForPage(offset, itemsPerPage);
        }

        return users;
    }

    private List<Client> fetchClientsByPage(Integer noOfPage, Integer itemsPerPage) {
        List<Client> clients;

        if (itemsPerPage == null) {
            clients = new ArrayList<>(clientRepository.findAll());
        } else if (noOfPage == null || noOfPage <= 1) {
            clients = clientRepository.findAllForFirstPate(itemsPerPage);
        } else {
            int offset = Utils.computeOffset(noOfPage, itemsPerPage);
            clients = clientRepository.findAllForPage(offset, itemsPerPage);
        }

        return clients;
    }

    private NotFoundResourceException userNotFound() {
        String message ="User not found!";
        List<String> errors = new ArrayList<>();
        errors.add("error.user.fetch-failed");
        errors.add("error.user.user-not-found");
        return new NotFoundResourceException(message, errors);
    }
}
