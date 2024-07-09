package it.uniroma3.siw.siwpers.controller;

import it.uniroma3.siw.siwpers.exceptions.AuthException;
import it.uniroma3.siw.siwpers.model.Role;
import it.uniroma3.siw.siwpers.model.User;
import it.uniroma3.siw.siwpers.repository.UserRepository;
import it.uniroma3.siw.siwpers.response.LoginResponse;
import it.uniroma3.siw.siwpers.service.JwtService;
import it.uniroma3.siw.siwpers.service.RoleService;
import it.uniroma3.siw.siwpers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtUtil;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtUtil, UserService userService, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByEmail(username);
        if (user != null) {

            //Controllo della password per l'utente trovato

            if (passwordEncoder.matches(password, user.getPassword())) {

                /* Caso in cui l'utente esiste e la password e' corretta */

                Map<String, Object> claims = new HashMap<>();
                claims.put("ruoli", user.getRoleList());

                /*
                Generazione del JWT <--> Chiamo il Builder e gli passo dei claims custom (username)
                 */

                String token = jwtUtil.generateToken(claims, user);
                return new LoginResponse(token, "Autenticazione completata con successo.", true, user.getRoleList(), user.getNome(), user.getId());
            } else throw new AuthException("Le credenziali inserite sono errate.", HttpStatus.BAD_REQUEST);
        }

        throw new AuthException("Le credenziali inserite sono errate.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {

            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setNome(registerRequest.getNome());
            user.setCognome(registerRequest.getCognome());
            user.setDob(registerRequest.getDob());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            List<Role> roles = new ArrayList<>();
            roles.add(roleService.getRoleById(1L));
            user.setRoleList(roles);
            userService.registerUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

