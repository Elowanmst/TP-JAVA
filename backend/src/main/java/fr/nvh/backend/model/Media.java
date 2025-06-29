package fr.nvh.backend.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Media {
    private String titre;
    private String description;
    private int anneeSortie;
    private String imageUrl;

    public Media() {}

    public Media(String titre, String description, int anneeSortie, String imageUrl) {
        this.titre = titre;
        this.description = description;
        this.anneeSortie = anneeSortie;
        this.imageUrl = imageUrl;
    }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getAnneeSortie() { return anneeSortie; }
    public void setAnneeSortie(int anneeSortie) { this.anneeSortie = anneeSortie; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}