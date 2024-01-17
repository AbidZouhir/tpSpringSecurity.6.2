package ma.work.springsecurity6.config;

import ma.work.springsecurity6.model.OurUser;
import ma.work.springsecurity6.repository.OurUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class OurUserInfoUsersDetailsService implements UserDetailsService {
    @Autowired
    private OurUserRepository ourUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OurUser> user=ourUserRepository.findByEmail(username);
        return user.map(OurUserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User does not exist."));
    }
}
