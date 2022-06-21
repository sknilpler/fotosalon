package ru.project.fotosalon.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.project.fotosalon.models.Sotrudnik;

import java.util.List;

public interface SotrudnikRepository extends CrudRepository<Sotrudnik, Long> {

    Sotrudnik findByUsername(String username);

    List<Sotrudnik> findAllByPost(String post);

    @Query(value = "SELECT sotrudnik.* FROM sotrudnik, user,user_roles,role\n" +
            "WHERE sotrudnik.username = user.username AND\n" +
            "user.id = user_roles.user_id AND\n" +
            "user_roles.roles_id = role.id AND\n" +
            "role.name = :role", nativeQuery = true)
    List<Sotrudnik> findByUserRole(@Param("role") String role);

    @Query(value = "SELECT sotrudnik.* FROM sotrudnik, usluga_sotrudnik WHERE sotrudnik.id = usluga_sotrudnik.sotrudnik_id AND usluga_sotrudnik.usluga_id = :id",nativeQuery = true)
    List<Sotrudnik> findAllByUsluga(@Param("id") Long id);

    @Query(value = "SELECT sotrudnik.id, sotrudnik.username, sotrudnik.fio, sotrudnik.post, sotrudnik.phone, sotrudnik.oklad, sotrudnik.premiya, sotrudnik.avatar, COUNT(grafik.id) as hours FROM sotrudnik, grafik\n" +
            "WHERE grafik.sotrudnik_id = sotrudnik.id AND\n" +
            "grafik.data >= :d1 AND  grafik.data <= :d2\n" +
            "GROUP BY sotrudnik.id",nativeQuery = true)
    List<Object[]> getZarplataStatistic(@Param("d1") String d1, @Param("d2") String d2);

}
