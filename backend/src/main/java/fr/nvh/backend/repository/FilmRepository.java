package fr.nvh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.nvh.backend.model.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {
    // Pas besoin d’implémenter, Spring Data s’en charge
}


