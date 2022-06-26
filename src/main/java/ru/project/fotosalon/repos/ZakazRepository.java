package ru.project.fotosalon.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.project.fotosalon.models.Zakaz;
import ru.project.fotosalon.utils.ZakazStatus;

import java.util.Date;
import java.util.List;

public interface ZakazRepository extends CrudRepository<Zakaz, Long> {
    List<Zakaz> findAllByClientId(Long id);

    @Query(value = "SELECT * FROM zakaz\n" +
            "WHERE order_date BETWEEN :d1 AND :d2\n" +
            "ORDER BY order_date", nativeQuery = true)
    List<Zakaz> findBetweenTwoDates(@Param("d1") Date d1, @Param("d2") Date d2);

    List<Zakaz> findAllByStatus(ZakazStatus status);


    @Query(value = "SELECT * FROM zakaz\n" +
            "WHERE order_date BETWEEN :d1 AND :d2 AND status = :stat\n" +
            "ORDER BY order_date", nativeQuery = true)
    List<Zakaz> findBetweenTwoDatesAndStatus(@Param("d1") Date d1, @Param("d2") Date d2, @Param("stat") String status);

    @Query(value = "SELECT zakaz.* FROM zakaz, usluga\n" +
            "WHERE zakaz.usluga_id = usluga.id AND\n" +
            "usluga.type = :tip", nativeQuery = true)
    List<Zakaz> findByUslugaType(@Param("tip") String tip);

    List<Zakaz> findAllBySotrudnikId(Long id);

    @Query(value = "SELECT * FROM zakaz WHERE order_date >= :d1 AND order_date <= :d2 AND sotrudnik_id = :id order by order_date", nativeQuery = true)
    List<Zakaz> findAllBySotrudnikIdAndDate(@Param("d1") String d1, @Param("d2") String d2, @Param("id") Long id);

    @Query(value = "SELECT COALESCE(SUM(zakaz.total_price),0) AS income, COALESCE(SUM(rashodnik.numbers*sklad.price),0) AS consumption FROM zakaz,rashodnik,sklad\n" +
            "WHERE\n" +
            "zakaz.order_date >= :d1 AND zakaz.order_date <= :d2 AND\n" +
            "zakaz.usluga_id = rashodnik.usluga_id AND\n" +
            "rashodnik.sklad_id = sklad.id;",nativeQuery = true)
    List<Object[]> getIncomeConsumptionByDate(@Param("d1") String d1, @Param("d2") String d2);
}
