package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.Skidka;

import java.util.List;

public interface SkidkaRepository extends CrudRepository<Skidka, Long> {

    List<Skidka> findAllBySotrudnikId(Long id);
    List<Skidka> findAllByClientId(Long id);
    List<Skidka> findAllBySotrudnikIdAndUslugaId(Long id_sotr, Long id_uslugi);
    List<Skidka> findAllBySotrudnikIdAndUslugaIdAndClientId(Long id_sotr, Long id_uslugi, Long id_client);
}
