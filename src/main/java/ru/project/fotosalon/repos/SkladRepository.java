package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.Sklad;

public interface SkladRepository extends CrudRepository<Sklad, Long> {
}
