package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.Rashodnik;

import java.util.List;

public interface RashodnikRepository extends CrudRepository<Rashodnik, Long> {
    List<Rashodnik> findAllBySkladId(Long id);
    List<Rashodnik> findAllByUslugaId(Long id);
}
