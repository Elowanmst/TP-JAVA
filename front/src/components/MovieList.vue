<script setup>
import { ref, onMounted } from "vue";

const movies = ref([]);
const myList = ref([]);
const userId = localStorage.getItem("userId");

async function fetchMovies() {
   const res = await fetch("http://localhost:8080/api/films");
   movies.value = await res.json();
}

async function fetchMyList() {
   if (!userId) {
      myList.value = [];
      return;
   }
   const res = await fetch(
      `http://localhost:8080/api/utilisateurs/${userId}/films`
   );
   if (res.ok) {
      myList.value = (await res.json()).map((f) => f.id);
   } else {
      myList.value = [];
   }
}

async function toggleMyList(movieId) {
   if (!userId) {
      alert('Connectez-vous pour utiliser "Ma liste"');
      return;
   }
   if (myList.value.includes(movieId)) {
      await fetch(
         `http://localhost:8080/api/utilisateurs/${userId}/films/${movieId}`,
         { method: "DELETE" }
      );
      myList.value = myList.value.filter((id) => id !== movieId);
   } else {
      await fetch(
         `http://localhost:8080/api/utilisateurs/${userId}/films/${movieId}`,
         { method: "POST" }
      );
      myList.value.push(movieId);
   }
}

function isInMyList(movieId) {
   return myList.value.includes(movieId);
}

onMounted(() => {
   fetchMovies();
   fetchMyList();
});

defineExpose({ fetchMovies });
</script>

<template>
   <div class="movie-list">
      <h2>Films populaires</h2>
      <div class="movies">
         <router-link
            v-for="movie in movies"
            :key="movie.id"
            :to="`/films/${movie.id}`"
            class="movie-card-link">
            <div class="movie-card" @click.stop>
               <button
                  class="star-btn"
                  :aria-label="
                     isInMyList(movie.id)
                        ? 'Retirer de ma liste'
                        : 'Ajouter à ma liste'
                  "
                  @click.stop.prevent="toggleMyList(movie.id)">
                  <svg
                     v-if="isInMyList(movie.id)"
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
                  <svg
                     v-else
                     xmlns="http://www.w3.org/2000/svg"
                     width="28"
                     height="28"
                     viewBox="0 0 24 24"
                     fill="#222"
                     stroke="#222"
                     stroke-width="2"
                     stroke-linecap="round"
                     stroke-linejoin="round">
                     <polygon
                        points="12 2 15 9 22 9.3 17 14 18.5 21 12 17.5 5.5 21 7 14 2 9.3 9 9 12 2"
                        fill="none" />
                  </svg>
               </button>
               <img
                  :src="
                     film.imageUrl || require('../assets/default-poster.jpeg')
                  "
                  alt="Affiche"
                  @error="
                     (event) =>
                        (event.target.src = require('../assets/default-poster.jpeg'))
                  " />
               <div>
                  <h3>{{ movie.titre }}</h3>
                  <p>{{ movie.description }}</p>
                  <p v-if="movie.anneeSortie">
                     Année : {{ movie.anneeSortie }}
                  </p>
               </div>
            </div>
         </router-link>
      </div>
   </div>
</template>

<style scoped>
.movie-list {
   margin-top: 2rem;
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
