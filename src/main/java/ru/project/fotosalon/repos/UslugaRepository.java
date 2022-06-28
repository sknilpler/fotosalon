package ru.project.fotosalon.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.project.fotosalon.models.Usluga;

import java.util.List;

public interface UslugaRepository extends CrudRepository<Usluga, Long> {

    @Query(value = "SELECT usluga.* from usluga, usluga_sotrudnik\n" +
            "WHERE usluga.id= usluga_sotrudnik.usluga_id AND\n" +
            "usluga_sotrudnik.sotrudnik_id = :id", nativeQuery = true)
    List<Usluga> findAllBySotrudnikId(@Param("id") Long id);

    List<Usluga> findAllByType(String type);

    @Query(value = "SELECT usluga.id, usluga.name, usluga.price, COUNT(usluga.id) as num, SUM(usluga.price) as total FROM usluga, zakaz \n" +
            "WHERE zakaz.usluga_id = usluga.id AND\n" +
            "zakaz.order_date BETWEEN :d1 AND :d2\n" +
            "GROUP BY 1\n" +
            "ORDER BY total DESC", nativeQuery = true)
    List<Object[]> getRendered(@Param("d1") String d1, @Param("d2") String d2);


    @Query(value = "SELECT COUNT(usluga.id) as num, COALESCE(SUM(usluga.price), 0) as total FROM usluga, zakaz  \n" +
            "WHERE zakaz.usluga_id = usluga.id AND\n" +
            "zakaz.order_date BETWEEN :d1 AND :d" +
            "2", nativeQuery = true)
    Object[] getUslugaTotalByDate(@Param("d1") String d1, @Param("d2") String d2);

    @Query(value = "SELECT usluga.id,usluga.name,usluga.price,usluga.file,usluga.type, COUNT(zakaz.id) as num FROM usluga, zakaz\n" +
            "WHERE usluga.id = zakaz.usluga_id AND\n" +
            "zakaz.order_date >= :d1 AND zakaz.order_date <= :d2 \n"+
            "GROUP BY usluga.id\n" +
            "ORDER BY num DESC", nativeQuery = true)
    List<Object[]> getPopularUsluga(@Param("d1") String d1, @Param("d2") String d2);

}
