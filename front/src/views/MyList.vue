<script setup>
const defaultPoster = new URL('../assets/default-poster.jpeg', import.meta.url).href
import { ref, onMounted } from "vue";

const userId = localStorage.getItem("userId");
const films = ref([]);
const series = ref([]);
const loading = ref(true);
const error = ref("");
const importError = ref("");
const importSuccess = ref("");
const fileInput = ref(null);

async function fetchMyList() {
   loading.value = true;
   error.value = "";
   if (!userId) {
      films.value = [];
      series.value = [];
      loading.value = false;
      return;
   }
   try {
      const [filmsRes, seriesRes] = await Promise.all([
         fetch(`http://localhost:8080/api/utilisateurs/${userId}/films`),
         fetch(`http://localhost:8080/api/utilisateurs/${userId}/series`),
      ]);
      if (!filmsRes.ok)
         throw new Error("Erreur lors du chargement de vos films");
      if (!seriesRes.ok)
         throw new Error("Erreur lors du chargement de vos séries");
      films.value = await filmsRes.json();
      series.value = await seriesRes.json();
   } catch (e) {
      error.value = e.message;
      films.value = [];
      series.value = [];
   } finally {
      loading.value = false;
   }
}

async function removeFromMyFilmList(filmId) {
   if (!userId) return;
   await fetch(
      `http://localhost:8080/api/utilisateurs/${userId}/films/${filmId}`,
      { method: "DELETE" }
   );
   films.value = films.value.filter((f) => f.id !== filmId);
}

async function removeFromMySerieList(serieId) {
   if (!userId) return;
   await fetch(
      `http://localhost:8080/api/utilisateurs/${userId}/series/${serieId}`,
      { method: "DELETE" }
   );
   series.value = series.value.filter((s) => s.id !== serieId);
}

// Exporter la liste
async function exportList() {
   if (!userId) return;
   try {
      const res = await fetch(`http://localhost:8080/api/utilisateurs/${userId}/export`);
      if (!res.ok) throw new Error("Erreur lors de l'export");
      const blob = await res.blob();
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = "ma-liste.json";
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
   } catch (e) {
      error.value = e.message;
   }
}

// Importer la liste
async function importList(event) {
   importError.value = "";
   importSuccess.value = "";
   const file = event.target.files[0];
   if (!file || !userId) return;
   try {
      const arrayBuffer = await file.arrayBuffer();
      const res = await fetch(`http://localhost:8080/api/utilisateurs/${userId}/import`, {
         method: "POST",
         headers: { "Content-Type": "application/json" },
         body: arrayBuffer,
      });
      if (!res.ok) throw new Error("Erreur lors de l'import");
      importSuccess.value = "Import réussi !";
      await fetchMyList();
   } catch (e) {
      importError.value = e.message;
   } finally {
      if (fileInput.value) fileInput.value.value = "";
   }
}

onMounted(fetchMyList);
</script>

<template>
   <div class="page">
      <h1>Ma liste</h1>
      <div v-if="!userId">
         <p>Connectez-vous pour voir votre liste personnalisée.</p>
      </div>
      <div v-else>
         <div class="import-export-bar">
            <button @click="exportList">Exporter ma liste</button>
            <label class="import-label">
               Importer une liste
               <input
                  type="file"
                  accept="application/json"
                  style="display: none"
                  ref="fileInput"
                  @change="importList"
               />
            </label>
         </div>
         <div v-if="importError" style="color: red">{{ importError }}</div>
         <div v-if="importSuccess" style="color: green">{{ importSuccess }}</div>
         <div v-if="loading">Chargement...</div>
         <div v-else-if="error" style="color: red">{{ error }}</div>
         <div v-else>
            <h2>Films</h2>
            <div v-if="films.length === 0">
               <p>Aucun film dans votre liste.</p>
            </div>
            <div v-else class="movies">
               <router-link
                  v-for="film in films"
                  :key="'film-' + film.id"
                  :to="`/films/${film.id}`"
                  class="movie-card-link">
                  <div class="movie-card" @click.stop>
                     <button
                        class="star-btn"
                        aria-label="Retirer de ma liste"
                        @click.stop.prevent="removeFromMyFilmList(film.id)">
                        <svg
                           xmlns="http://www.w3.org/2000/svg"
                           width="28"
                           height="28"
                           viewBox="0 0 24 24"
                           fill="#FFD600"
                           stroke="#222"
                           stroke-width="2"
                           stroke-linecap="round"
                           stroke-linejoin="round">
                           <polygon
                              points="12 2 15 9 22 9.3 17 14 18.5 21 12 17.5 5.5 21 7 14 2 9.3 9 9 12 2" />
                        </svg>
                     </button>
                     <img
                        :src="film.imageUrl || defaultPoster"
                        alt="Affiche"
                        @error="event.target.src = defaultPoster"
                     />
                     <div>
                        <h3>{{ film.titre }}</h3>
                        <p>{{ film.description }}</p>
                        <p v-if="film.anneeSortie">
                           Année : {{ film.anneeSortie }}
                        </p>
                     </div>
                  </div>
               </router-link>
            </div>

            <h2 style="margin-top: 2rem">Séries</h2>
            <div v-if="series.length === 0">
               <p>Aucune série dans votre liste.</p>
            </div>
            <div v-else class="movies">
               <router-link
                  v-for="serie in series"
                  :key="'serie-' + serie.id"
                  :to="`/series/${serie.id}`"
                  class="movie-card-link">
                  <div class="movie-card" @click.stop>
                     <button
                        class="star-btn"
                        aria-label="Retirer de ma liste"
                        @click.stop.prevent="removeFromMySerieList(serie.id)">
                        <svg
                           xmlns="http://www.w3.org/2000/svg"
                           width="28"
                           height="28"
                           viewBox="0 0 24 24"
                           fill="#FFD600"
                           stroke="#222"
                           stroke-width="2"
                           stroke-linecap="round"
                           stroke-linejoin="round">
                           <polygon
                              points="12 2 15 9 22 9.3 17 14 18.5 21 12 17.5 5.5 21 7 14 2 9.3 9 9 12 2" />
                        </svg>
                     </button>
                     <img
                        :src="serie.imageUrl || defaultPoster"
                        alt="Affiche"
                        @error="event.target.src = defaultPoster"
                     />
                     <div>
                        <h3>{{ serie.titre }}</h3>
                        <p>{{ serie.description }}</p>
                        <p v-if="serie.anneeSortie">
                           Année : {{ serie.anneeSortie }}
                        </p>
                     </div>
                  </div>
               </router-link>
            </div>
         </div>
      </div>
   </div>
</template>

<style scoped>
.page {
   margin-top: 100px;
}
.import-export-bar {
   display: flex;
   gap: 1rem;
   margin-bottom: 1.5rem;
}
.import-label {
   background: #222;
   color: #fff;
   border-radius: 6px;
   padding: 0.5em 1.2em;
   cursor: pointer;
   font-size: 1rem;
   display: inline-block;
   transition: background 0.2s;
}
.import-label:hover {
   background: #444;
}
.movies {
   display: flex;
   gap: 2rem;
   flex-wrap: wrap;
   margin-top: 2rem;
}
.movie-card {
   background: #fff;
   color: #222;
   border-radius: 8px;
   box-shadow: 0 2px 8px #0001;
   width: 200px;
   overflow: hidden;
   display: flex;
   flex-direction: column;
   position: relative;
}
.movie-card img {
   width: 100%;
   height: 300px;
   object-fit: cover;
}
.movie-card h3 {
   margin: 0.5rem 1rem 0 1rem;
}
.movie-card p {
   margin: 0.5rem 1rem 1rem 1rem;
   font-size: 0.95rem;
   color: #444;
}
.movie-card-link {
   text-decoration: none;
   color: inherit;
}
.movie-card-link:hover .movie-card {
   box-shadow: 0 4px 16px #0002;
   transform: translateY(-2px) scale(1.03);
   transition: 0.2s;
}
.star-btn {
   position: absolute;
   top: 10px;
   right: 10px;
   background: transparent;
   border: none;
   cursor: pointer;
   padding: 0;
   z-index: 2;
}
.star-btn svg {
   display: block;
}
</style>