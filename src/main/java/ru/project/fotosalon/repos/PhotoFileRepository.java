package ru.project.fotosalon.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.project.fotosalon.models.PhotoFile;

import java.util.List;

public interface PhotoFileRepository extends CrudRepository<PhotoFile, Long> {

    @Query(value = "SELECT photo_file.* FROM photo_file, portfolio\n" +
            "WHERE photo_file.portfolio_id = portfolio.id AND\n" +
            "portfolio.sotrudnik_id = :id",nativeQuery = true)
    List<PhotoFile> findBySotrudnik(@Param("id") Long id);
}
