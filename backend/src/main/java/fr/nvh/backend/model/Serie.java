package fr.nvh.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private int anneeSortie;
    private String imageUrl;

    // Constructeurs
    public Serie() {}

    public Serie(String titre, String description, int anneeSortie) {
        this.titre = titre;
        this.description = description;
        this.anneeSortie = anneeSortie;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getAnneeSortie() { return anneeSortie; }
    public void setAnneeSortie(int anneeSortie) { this.anneeSortie = anneeSortie; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}