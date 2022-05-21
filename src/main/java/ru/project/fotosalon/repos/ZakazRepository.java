package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.Zakaz;

public interface ZakazRepository extends CrudRepository<Zakaz, Long> {

}
