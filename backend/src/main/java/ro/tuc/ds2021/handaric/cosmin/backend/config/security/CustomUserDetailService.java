package ro.tuc.ds2021.handaric.cosmin.backend.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.tuc.ds2021.handaric.cosmin.backend.entities.user.AppUser;
import ro.tuc.ds2021.handaric.cosmin.backend.repositories.user.AppUserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AppUser userFromDB = appUserRepository.findAppUserByUsername(username);
        if (userFromDB == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = User.withUsername(userFromDB.getUsername())
                .password(userFromDB.getPassword())
                .roles(userFromDB.getClass().getSimpleName().toUpperCase()).build();

        return userDetails;
    }
}
