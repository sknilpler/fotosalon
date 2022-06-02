package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.Usluga;

import java.util.List;

public interface UslugaRepository extends CrudRepository<Usluga, Long> {

        List<Usluga> findAllBySotrudnikId(Long id);
}
