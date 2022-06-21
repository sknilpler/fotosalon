package ru.project.fotosalon.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.project.fotosalon.models.Sklad;

import java.util.Date;
import java.util.List;

public interface SkladRepository extends CrudRepository<Sklad, Long> {

    @Query(value = "SELECT * FROM sklad where name = :name AND type = :type AND units = :units", nativeQuery = true)
    List<Sklad> findByNameTypeUnits(@Param("name") String name, @Param("type") String type, @Param("units") String units);

    @Query(value = "SELECT * FROM sklad w WHERE " +
            "(w.name LIKE %:keyword% OR\n" +
            "  w.type LIKE %:keyword%)", nativeQuery = true)
    List<Sklad> findAllByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT sklad.*, SUM(rashodnik.numbers) as total FROM sklad,rashodnik,zakaz\n" +
            "WHERE sklad.id = rashodnik.sklad_id AND\n" +
            "rashodnik.usluga_id = zakaz.usluga_id AND\n" +
            "zakaz.order_date BETWEEN :d1 AND :d2\n" +
            "GROUP BY sklad.id",nativeQuery = true)
    List<Object[]> findRashodniksBeetwenDates(@Param("d1") Date d1, @Param("d2") Date d2);
}
