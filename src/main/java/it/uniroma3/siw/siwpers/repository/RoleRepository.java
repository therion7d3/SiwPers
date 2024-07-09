package it.uniroma3.siw.siwpers.repository;

import it.uniroma3.siw.siwpers.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findById(Long id);
}