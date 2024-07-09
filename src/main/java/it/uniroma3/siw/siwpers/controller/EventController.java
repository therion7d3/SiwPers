package it.uniroma3.siw.siwpers.controller;

import com.fasterxml.jackson.databind.JsonNode;
import it.uniroma3.siw.siwpers.model.Evento;
import it.uniroma3.siw.siwpers.model.User;
import it.uniroma3.siw.siwpers.response.EventResponse;
import it.uniroma3.siw.siwpers.service.UserService;
import it.uniroma3.siw.siwpers.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping("/addevent")
    public ResponseEntity<?> createEvent(@RequestBody JsonNode requestBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User currentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
        }

        try {
            Evento newEvent = new Evento();
            String title = requestBody.get("title").asText();
            String description = requestBody.get("description").asText();
            Long userId = requestBody.get("ownerId").asLong();
            int maxTickets = requestBody.get("maxTickets").asInt();
            String tipoEvento = requestBody.get("tipoEvento").asText();
            String dataEvento = requestBody.get("dataEvento").asText();
            String indirizzo = requestBody.get("indirizzo").asText();


            User eventOwner = (User)userService.loadById(userId);

            if (eventOwner == null) {
                return ResponseEntity.notFound().build();
            }

            newEvent.setIndirizzo(indirizzo);
            newEvent.setTitle(title);
            newEvent.setDescription(description);
            newEvent.setOwner(eventOwner);
            newEvent.setMaxTickets(maxTickets);
            newEvent.setTipoEvento(tipoEvento);
            newEvent.setDataEvento(dataEvento);
            eventService.saveEvent(newEvent);

            EventResponse response = new EventResponse(newEvent);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<Evento> events = eventService.getAllEventi();
        List<EventResponse> eventResponses = events.stream()
                .map(EventResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventResponses);
    }

    @GetMapping("/organizer/{id}")
    public ResponseEntity<List<EventResponse>> getEventsOrganizedByUser(@PathVariable("id") Long id) {
        User user = (User) userService.loadById(id);
        if (user != null) {
            List<Evento> events = eventService.getEventiByOwner(user);
            List<EventResponse> eventResponses = events.stream()
                    .map(EventResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(eventResponses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable("id") Long id) {
        Evento event = eventService.getEventoById(id);
        EventResponse response = new EventResponse(event);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRicettaById(@PathVariable("id") Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User currentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
        }

        User user = (User) authentication.getPrincipal();

        boolean isDeleted = eventService.deleteEventoIfOwner(id, user);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non sei autorizzato ad eliminare questo evento.");
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<?> modifyEventById(@PathVariable("id") Long id, @RequestBody JsonNode requestBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        Evento savedEvent = this.eventService.getEventoById(id);

        if (!(principal instanceof User currentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
        }

        User author = savedEvent.getOwner();

        if (!author.equals(currentUser) && !(currentUser.getRoleList().get(0).getId() == 2)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato a modificare questo evento.");
        }

        try {
            String title = requestBody.get("title").asText();
            String description = requestBody.get("description").asText();
            Long userId = requestBody.get("ownerId").asLong();
            int maxTickets = requestBody.get("maxTickets").asInt();
            String tipoEvento = requestBody.get("tipoEvento").asText();
            String dataEvento = requestBody.get("dataEvento").asText();
            String indirizzo = requestBody.get("indirizzo").asText();

            User autore = (User) userService.loadById(userId);

            if (autore == null) {
                return ResponseEntity.notFound().build();
            }

            savedEvent.setTitle(title);
            savedEvent.setIndirizzo(indirizzo);
            savedEvent.setMaxTickets(maxTickets);
            savedEvent.setTipoEvento(tipoEvento);
            savedEvent.setDataEvento(dataEvento);
            savedEvent.setDescription(description);
            eventService.saveEvent(savedEvent);

            EventResponse response = new EventResponse(savedEvent);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
