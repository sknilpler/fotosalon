package ru.project.fotosalon.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.project.fotosalon.models.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {


    Client findByUsername(String username);

    @Query(value="SELECT client.id, client.fio, COUNT(zakaz.id) as num, MAX(zakaz.order_date) as date FROM client, zakaz\n" +
            "WHERE zakaz.client_id = client.id AND\n" +
            "zakaz.order_date >= :d1 AND zakaz.order_date <= :d2 \n"+
            "GROUP BY client.id",nativeQuery = true)
    List<Object[]> getClientStatistic(@Param("d1") String d1, @Param("d2") String d2);

    @Query(value = "SELECT client.fio, usluga.name, zakaz.order_date, skidka.size, (usluga.price/100*skidka.size) AS total, sotrudnik.fio as sotrudnik \n" +
            "FROM client, skidka, usluga, sotrudnik, zakaz\n" +
            "WHERE client.id = skidka.client_id AND\n" +
            "usluga.id = skidka.usluga_id AND\n" +
            "sotrudnik.id = skidka.sotrudnik_id AND\n" +
            "usluga.id = zakaz.usluga_id AND\n" +
            "zakaz.order_date >= :d1 AND zakaz.order_date <= :d2\n" +
            "ORDER BY zakaz.order_date", nativeQuery = true)
    List<Object[]> getAllSkidka(@Param("d1") String d1, @Param("d2") String d2);
}
