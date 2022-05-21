package ru.project.fotosalon.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.project.fotosalon.models.Sklad;

import java.util.List;

public interface SkladRepository extends CrudRepository<Sklad, Long> {

    @Query(value = "SELECT * FROM sklad where name = :name AND type = :type AND units = :units", nativeQuery = true)
    List<Sklad> findByNameTypeUnits(@Param("name") String name, @Param("type") String type, @Param("units") String units);

    @Query(value = "SELECT * FROM sklad w WHERE " +
            "(w.name LIKE %:keyword% OR\n" +
            "  w.type LIKE %:keyword%)", nativeQuery = true)
    List<Sklad> findAllByKeyword(@Param("keyword") String keyword);
}
