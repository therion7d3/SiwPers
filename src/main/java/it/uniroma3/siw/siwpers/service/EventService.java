package it.uniroma3.siw.siwpers.service;

import it.uniroma3.siw.siwpers.model.Evento;
import it.uniroma3.siw.siwpers.model.User;
import it.uniroma3.siw.siwpers.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void saveEvent(Evento event) {
        eventRepository.save(event);
    }

    public List<Evento> getEventiByOwner(User user) {
        List<Evento> eventi = (List<Evento>) eventRepository.findByOwner(user);
        return eventi;
    }

    public List<Evento> getAllEventi() {
        List<Evento> eventi = (List<Evento>) eventRepository.findAll();
        return eventi;
    }

    public void deleteEventoById(long id) {
        eventRepository.deleteById(id);
    }

    public Evento getEventoById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public boolean deleteEventoIfOwner(Long eventoId, User user) {
        Evento evento = eventRepository.findById(eventoId).orElse(null);
        if (evento != null && evento.getOwner().equals(user)) {
            eventRepository.deleteById(eventoId);
            return true;
        }
        return false;
    }
}
