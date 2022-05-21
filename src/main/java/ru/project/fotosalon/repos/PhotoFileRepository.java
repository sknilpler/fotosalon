package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.PhotoFile;

public interface PhotoFileRepository extends CrudRepository<PhotoFile, Long> {
}
