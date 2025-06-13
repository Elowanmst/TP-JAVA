package fr.nvh.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.nvh.backend.model.Serie;
import fr.nvh.backend.repository.SerieRepository;

@RestController
@RequestMapping("/api/series")
@CrossOrigin(origins = "*")
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    @GetMapping
    public List<Serie> getAllSeries() {
        return serieRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Serie> getSerieById(@PathVariable Long id) {
        return serieRepository.findById(id);
    }

    @PostMapping
    public Serie createSerie(@RequestBody Serie serie) {
        return serieRepository.save(serie);
    }

    @PutMapping("/{id}")
    public Serie updateSerie(@PathVariable Long id, @RequestBody Serie serieDetails) {
        return serieRepository.findById(id).map(serie -> {
            serie.setTitre(serieDetails.getTitre());
            serie.setDescription(serieDetails.getDescription());
            serie.setAnneeSortie(serieDetails.getAnneeSortie());
            return serieRepository.save(serie);
        }).orElseGet(() -> {
            serieDetails.setId(id);
            return serieRepository.save(serieDetails);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteSerie(@PathVariable Long id) {
        serieRepository.deleteById(id);
    }
}