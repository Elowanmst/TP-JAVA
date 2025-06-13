package fr.nvh.backend.model;

import jakarta.persistence.*;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auteur;  // nom de la personne qui note

    private int valeur;  // par exemple de 1 à 5

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    public Note() {}

    public Note(String auteur, int valeur, Film film) {
        this.auteur = auteur;
        this.valeur = valeur;
        this.film = film;
    }

    // getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public int getValeur() { return valeur; }
    public void setValeur(int valeur) { this.valeur = valeur; }

    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
}