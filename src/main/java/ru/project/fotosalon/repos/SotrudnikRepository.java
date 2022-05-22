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

}
