package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
