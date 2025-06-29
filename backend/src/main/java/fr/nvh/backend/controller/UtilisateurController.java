package fr.nvh.backend.controller;

import fr.nvh.backend.model.Utilisateur;
import fr.nvh.backend.model.Film;
import fr.nvh.backend.model.Serie;
import fr.nvh.backend.repository.UtilisateurRepository;
import fr.nvh.backend.repository.FilmRepository;
import fr.nvh.backend.repository.SerieRepository;
import fr.nvh.backend.exception.UtilisateurNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.nvh.backend.util.UtilisateurListIO;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

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
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
    }

    @PostMapping("/{id}/films/{filmId}")
    public void addFilmToList(@PathVariable Long id, @PathVariable Long filmId) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film non trouvé"));
        utilisateur.getFilms().add(film);
        utilisateurRepository.save(utilisateur);
    }

    @DeleteMapping("/{id}/films/{filmId}")
    public void removeFilmFromList(@PathVariable Long id, @PathVariable Long filmId) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film non trouvé"));
        utilisateur.getFilms().remove(film);
        utilisateurRepository.save(utilisateur);
    }

    @GetMapping("/{id}/series")
    public Set<Serie> getSeries(@PathVariable Long id) {
        return utilisateurRepository.findById(id)
                .map(Utilisateur::getSeries)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
    }

    @PostMapping("/{id}/series/{serieId}")
    public void addSerieToList(@PathVariable Long id, @PathVariable Long serieId) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RuntimeException("Série non trouvée"));
        utilisateur.getSeries().add(serie);
        utilisateurRepository.save(utilisateur);
    }

    @DeleteMapping("/{id}/series/{serieId}")
    public void removeSerieFromList(@PathVariable Long id, @PathVariable Long serieId) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
        Serie serie = serieRepository.findById(serieId)
                .orElseThrow(() -> new RuntimeException("Série non trouvée"));
        utilisateur.getSeries().remove(serie);
        utilisateurRepository.save(utilisateur);
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<byte[]> exportUserList(@PathVariable Long id) throws Exception {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
        Path tempFile = Files.createTempFile("user-list-", ".json");
        UtilisateurListIO.exportList(utilisateur, tempFile);
        byte[] fileContent = Files.readAllBytes(tempFile);
        Files.delete(tempFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ma-liste.json\"")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fileContent);
    }

    @PostMapping("/{id}/import")
    public ResponseEntity<String> importUserList(@PathVariable Long id, @RequestBody byte[] fileContent) throws Exception {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
        Path tempFile = Files.createTempFile("user-list-import-", ".json");
        Files.write(tempFile, fileContent);
        UtilisateurListIO.importList(utilisateur, tempFile, filmRepository, serieRepository);
        utilisateurRepository.save(utilisateur);
        Files.delete(tempFile);
        return ResponseEntity.ok("Import effectué");
    }
}