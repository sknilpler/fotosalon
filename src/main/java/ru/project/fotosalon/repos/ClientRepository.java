package ru.project.fotosalon.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {


    Client findByUsername(String username);

    @Query(value="SELECT client.id, client.fio, COUNT(zakaz.id) as num, MAX(zakaz.order_date) as date FROM client, zakaz\n" +
            "WHERE zakaz.client_id = client.id\n" +
            "GROUP BY client.id",nativeQuery = true)
    List<Object[]> getClientStatistic();
}
