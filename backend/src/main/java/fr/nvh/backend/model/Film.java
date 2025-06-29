package fr.nvh.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Film extends Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Film() {}

    public Film(String titre, String description, int anneeSortie, String imageUrl) {
        super(titre, description, anneeSortie, imageUrl);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}