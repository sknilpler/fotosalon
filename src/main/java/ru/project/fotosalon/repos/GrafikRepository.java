package ru.project.fotosalon.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.project.fotosalon.models.Grafik;

import java.util.Date;
import java.util.List;

public interface GrafikRepository extends CrudRepository<Grafik, Long> {

    List<Grafik> findAllBySotrudnikId(Long id);

    @Query(value = "SELECT * FROM grafik \n" +
            "WHERE grafik.sotrudnik_id = :id AND grafik.data >= :d1 AND grafik.data <= :d2",nativeQuery = true)
    List<Grafik> findBySotrudnikAndDate(@Param("id") Long id,@Param("d1") Date d1, @Param("d2") Date d2);

    @Query(value = "SELECT * FROM grafik WHERE grafik.id_zakaz = :id_z AND grafik.sotrudnik_id = :id_s", nativeQuery = true)
    List<Grafik> findByZakazAndSotrudnik(@Param("id_z") Long id_z, @Param("id_s") Long id_s);

}
