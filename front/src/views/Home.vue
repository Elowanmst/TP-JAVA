<script setup>
import { ref } from "vue";
import MovieList from "../components/MovieList.vue";
import SerieList from "../components/SerieList.vue";

const showFilmForm = ref(false);
const showSerieForm = ref(false);
const title = ref("");
const description = ref("");
const year = ref("");
const imageUrl = ref("");

const serieTitle = ref("");
const serieDescription = ref("");
const serieYear = ref("");
const serieImageUrl = ref("");

const isSubmitting = ref(false);
const error = ref("");

const movieListRef = ref(null);
const serieListRef = ref(null);

const openFilmForm = () => {
   showFilmForm.value = true;
   error.value = "";
};
const closeFilmForm = () => {
   showFilmForm.value = false;
   title.value = "";
   description.value = "";
   year.value = "";
   imageUrl.value = "";
   error.value = "";
};

const openSerieForm = () => {
   showSerieForm.value = true;
   error.value = "";
};
const closeSerieForm = () => {
   showSerieForm.value = false;
   serieTitle.value = "";
   serieDescription.value = "";
   serieYear.value = "";
   serieImageUrl.value = "";
   error.value = "";
};

const submitFilmForm = async () => {
   isSubmitting.value = true;
   error.value = "";
   try {
      const response = await fetch("http://localhost:8080/api/films", {
         method: "POST",
         headers: { "Content-Type": "application/json" },
         body: JSON.stringify({
            titre: title.value,
            description: description.value,
            anneeSortie: parseInt(year.value, 10),
            imageUrl: imageUrl.value,
         }),
      });
      if (!response.ok) throw new Error("Erreur lors de l'ajout du film");
      if (movieListRef.value && movieListRef.value.fetchMovies) {
         await movieListRef.value.fetchMovies();
      }
      closeFilmForm();
   } catch (e) {
      error.value = e.message;
   } finally {
      isSubmitting.value = false;
   }
};

const submitSerieForm = async () => {
   isSubmitting.value = true;
   error.value = "";
   try {
      const response = await fetch("http://localhost:8080/api/series", {
         method: "POST",
         headers: { "Content-Type": "application/json" },
         body: JSON.stringify({
            titre: serieTitle.value,
            description: serieDescription.value,
            anneeSortie: parseInt(serieYear.value, 10),
            imageUrl: serieImageUrl.value,
         }),
      });
      if (!response.ok) throw new Error("Erreur lors de l'ajout de la série");
      if (serieListRef.value && serieListRef.value.fetchSeries) {
         await serieListRef.value.fetchSeries();
      }
      closeSerieForm();
   } catch (e) {
      error.value = e.message;
   } finally {
      isSubmitting.value = false;
   }
};
</script>

<template>
   <div class="header-container">
      <header>
         <img
            alt="Logo"
            class="logo"
            src="../assets/logo-noir.png"
            width="225"
            height="225" />
         <h1>Bienvenue sur MovieTracker</h1>
         <p>
            Suivez vos films et séries préférés, ajoutez-les à votre liste et
            marquez votre progression !
         </p>
      </header>
      <div class="cta-links">
         <router-link to="/movies" class="cta">Voir les films</router-link>
         <router-link to="/series" class="cta">Voir les séries</router-link>
         <router-link to="/my-list" class="cta">Ma liste</router-link>
      </div>
      <button class="add-btn" @click="openFilmForm">Ajouter un film</button>
      <button class="add-btn" @click="openSerieForm">Ajouter une série</button>

      <!-- Formulaire Film -->
      <form
         v-if="showFilmForm"
         class="add-movie-form"
         @submit.prevent="submitFilmForm">
         <h2>Ajouter un film</h2>
         <div>
            <label>Titre</label>
            <input v-model="title" required />
         </div>
         <div>
            <label>Description</label>
            <textarea v-model="description" required></textarea>
         </div>
         <div>
            <label>Année de sortie</label>
            <input
               v-model="year"
               type="number"
               min="1900"
               max="2100"
               required />
         </div>
         <div>
            <label>Affiche (URL)</label>
            <input v-model="imageUrl" type="url" placeholder="https://..." />
         </div>
         <div class="form-actions">
            <button type="submit" :disabled="isSubmitting">Ajouter</button>
            <button type="button" @click="closeFilmForm">Annuler</button>
         </div>
         <div v-if="error" class="error">{{ error }}</div>
      </form>

      <!-- Formulaire Série -->
      <form
         v-if="showSerieForm"
         class="add-movie-form"
         @submit.prevent="submitSerieForm">
         <h2>Ajouter une série</h2>
         <div>
            <label>Titre</label>
            <input v-model="serieTitle" required />
         </div>
         <div>
            <label>Description</label>
            <textarea v-model="serieDescription" required></textarea>
         </div>
         <div>
            <label>Année de sortie</label>
            <input
               v-model="serieYear"
               type="number"
               min="1900"
               max="2100"
               required />
         </div>
         <div>
            <label>Affiche (URL)</label>
            <input
               v-model="serieImageUrl"
               type="url"
               placeholder="https://..." />
         </div>
         <div class="form-actions">
            <button type="submit" :disabled="isSubmitting">Ajouter</button>
            <button type="button" @click="closeSerieForm">Annuler</button>
         </div>
         <div v-if="error" class="error">{{ error }}</div>
      </form>

      <MovieList ref="movieListRef" />
      <SerieList ref="serieListRef" />
   </div>
</template>

<style scoped>
.header-container {
   display: flex;
   flex-direction: column;
   align-items: center;
   margin-top: 80px;
}
header {
   text-align: center;
   margin: 2rem 0 2rem 0;
}
.logo {
   display: block;
   margin: 0 auto 1rem;
}
.cta-links {
   display: flex;
   gap: 1rem;
   margin-bottom: 2rem;
}
.cta {
   background: #fff;
   color: #222;
   padding: 0.4em 1em;
   text-decoration: none;
   font-weight: bold;
   border-radius: 4px;
   border: 1px solid #ddd;
   transition:
      background 0.2s,
      color 0.2s;
}
.cta:hover {
   background: #eee;
   color: #111;
}
h1 {
   font-size: 2.2rem;
   margin-bottom: 0.5rem;
}
p {
   color: #555;
   font-size: 1.1rem;
}
.add-btn {
   margin: 1rem 0;
   padding: 0.5em 1.2em;
   background: #222;
   color: #fff;
   border: none;
   border-radius: 6px;
   font-size: 1rem;
   cursor: pointer;
   transition: background 0.2s;
}
.add-btn:hover {
   background: #444;
}
.add-movie-form {
   background: #fff;
   border: 1px solid #ddd;
   border-radius: 8px;
   padding: 1.5rem;
   margin-bottom: 2rem;
   width: 320px;
   box-shadow: 0 2px 8px #0001;
   display: flex;
   flex-direction: column;
   gap: 1rem;
   color: #222;
}
.add-movie-form label {
   font-weight: bold;
   margin-bottom: 0.2rem;
}
.add-movie-form input,
.add-movie-form textarea {
   width: 100%;
   padding: 0.4em;
   border: 1px solid #ccc;
   border-radius: 4px;
}
.form-actions {
   display: flex;
   gap: 1rem;
   justify-content: flex-end;
}
.error {
   color: #c00;
   font-size: 0.95em;
   margin-top: 0.5em;
}
</style>
