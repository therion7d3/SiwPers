package it.uniroma3.siw.siwpers.service;

import it.uniroma3.siw.siwpers.model.Role;
import it.uniroma3.siw.siwpers.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAllRicette() {
        return (List<Role>) roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

}
