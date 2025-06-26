package fr.nvh.backend.controller;

import fr.nvh.backend.model.Utilisateur;
import fr.nvh.backend.model.Film;
import fr.nvh.backend.model.Serie;
import fr.nvh.backend.repository.UtilisateurRepository;
import fr.nvh.backend.repository.FilmRepository;
import fr.nvh.backend.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private SerieRepository serieRepository;

    @PostMapping("/register")
    public Utilisateur register(@RequestBody Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @PostMapping("/login")
    public Utilisateur login(@RequestBody Utilisateur utilisateur) {
        return utilisateurRepository.findByUsername(utilisateur.getUsername())
                .filter(u -> u.getPassword().equals(utilisateur.getPassword()))
                .orElseThrow(() -> new RuntimeException("Identifiants invalides"));
    }

    @GetMapping("/{id}/films")
    public Set<Film> getFilms(@PathVariable Long id) {
        return utilisateurRepository.findById(id)
                .map(Utilisateur::getFilms)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @PostMapping("/{id}/films/{filmId}")
    public void addFilmToList(@PathVariable Long id, @PathVariable Long filmId) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film non trouvé"));
        utilisateur.getFilms().add(film);
        utilisateurRepository.save(utilisateur);
    }

    @DeleteMapping("/{id}/films/{filmId}")
    public void removeFilmFromList(@PathVariable Long id, @PathVariable Long filmId) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film non trouvé"));
        utilisateur.getFilms().remove(film);
        utilisateurRepository.save(utilisateur);
    }

    @GetMapping("/{id}/series")
    public Set<Serie> getSeries(@PathVariable Long id) {
        return utilisateurRepository.findById(id)
                .map(Utilisateur::getSeries)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @PostMapping("/{id}/series/{serieId}")
    public void addSerieToList(@PathVariable Long id, @PathVariable Long serieId) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RuntimeException("Série non trouvée"));
        utilisateur.getSeries().add(serie);
        utilisateurRepository.save(utilisateur);
    }

    @DeleteMapping("/{id}/series/{serieId}")
    public void removeSerieFromList(@PathVariable Long id, @PathVariable Long serieId) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RuntimeException("Série non trouvée"));
        utilisateur.getSeries().remove(serie);
        utilisateurRepository.save(utilisateur);
    }
}