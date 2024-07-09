package it.uniroma3.siw.siwpers.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class SiwAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserService servizioUtente;

    public SiwAuthenticationProvider(UserService userService) {
        this.servizioUtente = userService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails userDetails = servizioUtente.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email or password.");
        }
        return userDetails;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = servizioUtente.loadUserByUsername(username);

        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Password incorrect");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}
