package fr.nvh.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.nvh.backend.model.Note;
import fr.nvh.backend.model.Film;
import fr.nvh.backend.repository.NoteRepository;
import fr.nvh.backend.repository.FilmRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/film/{filmId}")
    public List<Note> getNotesByFilm(@PathVariable Long filmId) {
        return noteRepository.findByFilmId(filmId);
    }

    @PostMapping("/film/{filmId}")
    public Note addNote(@PathVariable Long filmId, @RequestBody Note note) {
        Optional<Film> filmOpt = filmRepository.findById(filmId);
        if (filmOpt.isPresent()) {
            note.setFilm(filmOpt.get());
            return noteRepository.save(note);
        } else {
            throw new RuntimeException("Film not found");
        }
    }
}