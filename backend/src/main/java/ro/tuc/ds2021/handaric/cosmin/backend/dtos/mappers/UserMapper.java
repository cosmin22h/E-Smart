package ro.tuc.ds2021.handaric.cosmin.backend.dtos.mappers;

import ro.tuc.ds2021.handaric.cosmin.backend.dtos.user.*;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.*;
import ro.tuc.ds2021.handaric.cosmin.backend.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class UserMapper {

    public static AppUser mapRegisterUserDtoToUserModel(RegisterUserDTO registerUserDto) {
        AppUser user = null;
        if (registerUserDto.getRole().equals("ADMIN")) {
            user = new Admin(
                    null,
                    registerUserDto.getUsername(),
                    registerUserDto.getEmail(),
                    registerUserDto.getPassword(),
                    new Date(),
                    new ArrayList<>());
        } else if (registerUserDto.getRole().equals("CLIENT")) {
            Address address = mapAddressDtoToModel(registerUserDto.getAddress());
            user = new Client(
                    null,
                    registerUserDto.getUsername(),
                    registerUserDto.getEmail(),
                    registerUserDto.getPassword(),
                    new Date(),
                    new ArrayList<>(),
                    registerUserDto.getFirstName(),
                    registerUserDto.getLastName(),
                    false,
                    registerUserDto.getBirthday(),
                    address);
        }
        return user;
    }

    public static Address mapAddressDtoToModel(AddressDTO addressDTO) {
        return Address.builder()
                .country(addressDTO.getCountry())
                .region(addressDTO.getRegion())
                .addressOne(addressDTO.getAddressOne())
                .build();
    }

    public static AddressDTO mapAddressModelToModel(Address address) {
        return AddressDTO.builder()
                .country(address.getCountry())
                .region(address.getRegion())
                .addressOne(address.getAddressOne())
                .build();
    }

    public static UserDTO mapUserModelToDto(AppUser user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getClass().getSimpleName().toUpperCase())
                .joinedDate(user.getJoinedDate())
                .build();
    }

    public static UserDTO mapUserModelToDtoWithLastSession(AppUser user, AuthSession authSession) {
        UserDTO userDTO = mapUserModelToDto(user);
        userDTO.setLastSession(mapAuthSessionModeToDto(authSession));

        return userDTO;
    }

    public static ClientDTO mapClientModelToDto(Client client) {
        AddressDTO address = mapAddressModelToModel(client.getAddress());
        AuthSessionDTO lastSession = mapAuthSessionModeToDto(Utils.getLastSession(client.getAuthSessions()));
        ClientDTO clientDto = new ClientDTO();
        clientDto.setId(client.getId());
        clientDto.setUsername(client.getUsername());
        clientDto.setEmail(client.getEmail());
        clientDto.setJoinedDate(client.getJoinedDate());
        clientDto.setFirstName(client.getFirsName());
        clientDto.setLastName(client.getLastName());
        clientDto.setDisabled(client.isDisabled());
        clientDto.setBirthday(client.getBirthday());
        clientDto.setAddress(address);
        clientDto.setNoOfDevices(client.getDevices().size());
        clientDto.setRole(client.getClass().getSimpleName().toUpperCase());
        clientDto.setLastSession(lastSession);

        return clientDto;
    }

    public static ClientDTO mapClientModelToDtoWithLastSession(Client client, AuthSession lastSession) {
        ClientDTO clientDTO = mapClientModelToDto(client);
        clientDTO.setLastSession(mapAuthSessionModeToDto(lastSession));

        return clientDTO;
    }

    public static AuthSessionDTO mapAuthSessionModeToDto(AuthSession authSession) {
        if (authSession == null) {
            return null;
        }
        return AuthSessionDTO.builder()
                .id(authSession.getId())
                .loginTime(authSession.getLoginTime())
                .logoutTime(authSession.getLogoutTime())
                .build();
    }
}
