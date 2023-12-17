package com.hadit1993.realestate.api.security;



import com.hadit1993.realestate.api.features.users.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RealEstateDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public RealEstateDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        if(user == null) throw new UsernameNotFoundException("no user found with this email");
        return RealEstateUserDetails.create(user);
    }


}
