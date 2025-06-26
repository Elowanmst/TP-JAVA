package fr.nvh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.nvh.backend.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}