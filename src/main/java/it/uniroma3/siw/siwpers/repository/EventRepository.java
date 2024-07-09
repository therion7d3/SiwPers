package it.uniroma3.siw.siwpers.repository;

import it.uniroma3.siw.siwpers.model.User;
import it.uniroma3.siw.siwpers.model.Evento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Evento, Long> {
    Evento findByTitle(String title);
    List<Evento> findByOwner(User user);
}