package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {


    Client findByUsername(String username);
}
