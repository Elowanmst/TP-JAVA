package fr.nvh.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.nvh.backend.model.Commentaire;
import fr.nvh.backend.model.Film;
import fr.nvh.backend.repository.CommentaireRepository;
import fr.nvh.backend.repository.FilmRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commentaires")
@CrossOrigin(origins = "*")
public class CommentaireController {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/film/{filmId}")
    public List<Commentaire> getCommentairesByFilm(@PathVariable Long filmId) {
        return commentaireRepository.findByFilmId(filmId);
    }

    @PostMapping("/film/{filmId}")
    public Commentaire addCommentaire(@PathVariable Long filmId, @RequestBody Commentaire commentaire) {
        Optional<Film> filmOpt = filmRepository.findById(filmId);
        if (filmOpt.isPresent()) {
            commentaire.setFilm(filmOpt.get());
            commentaire.setDateCreation(LocalDateTime.now());
            return commentaireRepository.save(commentaire);
        } else {
            throw new RuntimeException("Film not found");
        }
    }
}