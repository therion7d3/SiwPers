package it.uniroma3.siw.siwpers.repository;

import it.uniroma3.siw.siwpers.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}