package ru.project.fotosalon.repos;

import org.springframework.data.repository.CrudRepository;
import ru.project.fotosalon.models.Portfolio;

public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
}
