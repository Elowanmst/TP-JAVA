package fr.nvh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.nvh.backend.model.Commentaire;
import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByFilmId(Long filmId);
}