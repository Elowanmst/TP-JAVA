package fr.nvh.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.nvh.backend.model.Film;
import fr.nvh.backend.repository.FilmRepository;

@RestController
@RequestMapping("/api/films")
@CrossOrigin(origins = "*")  // Pour autoriser les requêtes de ton frontend
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Film> getFilmById(@PathVariable Long id) {
        return filmRepository.findById(id);
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmRepository.save(film);
    }

    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable Long id, @RequestBody Film filmDetails) {
        return filmRepository.findById(id).map(film -> {
            film.setTitre(filmDetails.getTitre());
            film.setDescription(filmDetails.getDescription());
            film.setAnneeSortie(filmDetails.getAnneeSortie());
            return filmRepository.save(film);
        }).orElseGet(() -> {
            filmDetails.setId(id);
            return filmRepository.save(filmDetails);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmRepository.deleteById(id);
    }
}
