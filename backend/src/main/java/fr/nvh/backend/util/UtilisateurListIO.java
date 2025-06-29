package fr.nvh.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.nvh.backend.model.Film;
import fr.nvh.backend.model.Serie;
import fr.nvh.backend.model.Utilisateur;
import fr.nvh.backend.repository.FilmRepository;
import fr.nvh.backend.repository.SerieRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;

public final class UtilisateurListIO {

    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void exportList(Utilisateur utilisateur, Path path) throws IOException {
        var data = new HashMap<String, Object>();
        data.put("films", utilisateur.getFilms());
        data.put("series", utilisateur.getSeries());
        mapper.writeValue(Files.newBufferedWriter(path), data);
    }

    public static void importList(
        Utilisateur utilisateur,
        Path path,
        FilmRepository filmRepository,
        SerieRepository serieRepository
    ) throws IOException {
        var data = mapper.readValue(Files.newBufferedReader(path), HashMap.class);

        // Récupérer les IDs des films et séries depuis le JSON
        var filmIds = (Iterable<?>) data.get("films");
        var serieIds = (Iterable<?>) data.get("series");

        utilisateur.getFilms().clear();
        utilisateur.getSeries().clear();

        if (filmIds != null) {
            for (Object obj : filmIds) {
                Long id = null;
                if (obj instanceof Integer) id = ((Integer) obj).longValue();
                else if (obj instanceof Long) id = (Long) obj;
                else if (obj instanceof HashMap) {
                    Object idObj = ((HashMap<?, ?>) obj).get("id");
                    if (idObj instanceof Integer) id = ((Integer) idObj).longValue();
                    else if (idObj instanceof Long) id = (Long) idObj;
                }
                if (id != null) {
                    filmRepository.findById(id).ifPresent(utilisateur.getFilms()::add);
                }
            }
        }
        if (serieIds != null) {
            for (Object obj : serieIds) {
                Long id = null;
                if (obj instanceof Integer) id = ((Integer) obj).longValue();
                else if (obj instanceof Long) id = (Long) obj;
                else if (obj instanceof HashMap) {
                    Object idObj = ((HashMap<?, ?>) obj).get("id");
                    if (idObj instanceof Integer) id = ((Integer) idObj).longValue();
                    else if (idObj instanceof Long) id = (Long) idObj;
                }
                if (id != null) {
                    serieRepository.findById(id).ifPresent(utilisateur.getSeries()::add);
                }
            }
        }
    }
}