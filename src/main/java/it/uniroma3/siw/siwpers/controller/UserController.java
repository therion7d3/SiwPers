package it.uniroma3.siw.siwpers.controller;

import it.uniroma3.siw.siwpers.model.Evento;
import it.uniroma3.siw.siwpers.repository.UserRepository;
import it.uniroma3.siw.siwpers.service.UserService;
import it.uniroma3.siw.siwpers.service.JwtService;
import it.uniroma3.siw.siwpers.response.UserResponse;
import it.uniroma3.siw.siwpers.model.User;
import it.uniroma3.siw.siwpers.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {

    final UserService userService;
    final UserRepository userRepository;
    final JwtService jwtService;
    private final EventService eventService;

    public UserController(UserService userService, UserRepository userRepository, JwtService jwtService, EventService eventService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getListaUtenti() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(new UserResponse(user.getUsername(), user.getNome(), user.getCognome(), user.getDob(), user.getId()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        User user = (User) userService.loadById(id);
        UserResponse response = new UserResponse(user.getUsername(), user.getNome(), user.getCognome(), user.getDob(), user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        User user = (User) userService.loadById(id);
        List<Evento> eventi = new ArrayList<>(eventService.getAllEventi());
        for (Evento evento : eventi) {
            if (evento.getOwner() == user) {
                eventService.deleteEventoById(evento.getId());
            }
        }
        this.userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.ACCEPTED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser(@RequestHeader("Authorization") String authorizationHeader) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String token = authorizationHeader.substring(7);
        System.out.println("Token: " + token);
        if (principal instanceof User currentUser && this.jwtService.isTokenValid(token, currentUser)) {
            System.out.println("Token valido.");
            UserResponse userResponse = new UserResponse(currentUser.getUsername(), currentUser.getNome(), currentUser.getCognome(), currentUser.getDob(), currentUser.getId());
            return ResponseEntity.ok(userResponse);
        }
        else {
            System.out.println("Richiesta non valida.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/modify/me")
    public ResponseEntity<UserResponse> modifyUser(@RequestBody User modifiedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userRepository.findByEmail(username);

        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (modifiedUser.getNome() != null) {
            currentUser.setNome(modifiedUser.getNome());
        }

        if (modifiedUser.getCognome() != null) {
            currentUser.setCognome(modifiedUser.getCognome());
        }

        if (modifiedUser.getEmail() != null) {
            currentUser.setEmail(modifiedUser.getEmail());
        }

        if (modifiedUser.getDob() != null) {
            currentUser.setDob(modifiedUser.getDob());
        }

        userRepository.save(currentUser);

        UserResponse userResponse = new UserResponse(currentUser.getUsername(), currentUser.getNome(), currentUser.getCognome(), currentUser.getDob(), currentUser.getId());
        return ResponseEntity.ok(userResponse);
    }
}
