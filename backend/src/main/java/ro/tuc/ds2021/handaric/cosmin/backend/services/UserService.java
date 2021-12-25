package ro.tuc.ds2021.handaric.cosmin.backend.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.*;

import java.util.List;
import java.util.UUID;

@Component
public interface UserService {
    List<UserDTO> fetchUsers(Integer noOfPage, Integer itemsPerPage);
    List<ClientDTO> fetchClients(Integer noOfPage, Integer itemsPerPage);
    UserDTO fetchAdmin(UUID id);
    ClientDTO fetchClient(UUID id);
    UUID updateAdmin(UserDTO adminDTO);
    UUID updateClient(ClientDTO clientDTO);
    UUID changePassword(ChangePasswordDTO changePasswordDTO);
    void deleteUser(UUID id);
}
