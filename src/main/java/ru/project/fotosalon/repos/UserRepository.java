package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
