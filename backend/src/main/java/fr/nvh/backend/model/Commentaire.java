package fr.nvh.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auteur;  // nom de la personne qui commente

    @Column(length = 1000)
    private String contenu;

    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    public Commentaire() {}

    public Commentaire(String auteur, String contenu, LocalDateTime dateCreation, Film film) {
        this.auteur = auteur;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.film = film;
    }

    // getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
}