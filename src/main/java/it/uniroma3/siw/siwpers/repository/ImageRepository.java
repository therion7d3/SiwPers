package it.uniroma3.siw.siwpers.repository;
import it.uniroma3.siw.siwpers.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUserId(Long userId);
    Optional<Image> findByEventoId(Long eventId);
}
