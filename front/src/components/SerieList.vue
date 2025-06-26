<script setup>
import { ref, onMounted } from "vue";

const series = ref([]);
const myList = ref([]);
const userId = localStorage.getItem("userId");

async function fetchSeries() {
   try {
      const response = await fetch("http://localhost:8080/api/series");
      series.value = await response.json();
   } catch (e) {
      series.value = [];
   }
}

async function fetchMyList() {
   if (!userId) {
      myList.value = [];
      return;
   }
   const res = await fetch(
      `http://localhost:8080/api/utilisateurs/${userId}/series`
   );
   if (res.ok) {
      myList.value = (await res.json()).map((s) => s.id);
   } else {
      myList.value = [];
   }
}

async function toggleMyList(serieId) {
   if (!userId) {
      alert('Connectez-vous pour utiliser "Ma liste"');
      return;
   }
   if (myList.value.includes(serieId)) {
      await fetch(
         `http://localhost:8080/api/utilisateurs/${userId}/series/${serieId}`,
         { method: "DELETE" }
      );
      myList.value = myList.value.filter((id) => id !== serieId);
   } else {
      await fetch(
         `http://localhost:8080/api/utilisateurs/${userId}/series/${serieId}`,
         { method: "POST" }
      );
      myList.value.push(serieId);
   }
}

function isInMyList(serieId) {
   return myList.value.includes(serieId);
}

onMounted(() => {
   fetchSeries();
   fetchMyList();
});

defineExpose({ fetchSeries });
</script>

<template>
   <div class="serie-list">
      <h2>Séries populaires</h2>
      <div class="series">
         <div v-for="serie in series" :key="serie.id" class="serie-card">
            <button
               class="star-btn"
               :aria-label="
                  isInMyList(serie.id)
                     ? 'Retirer de ma liste'
                     : 'Ajouter à ma liste'
               "
               @click.stop.prevent="toggleMyList(serie.id)">
               <svg
                  v-if="isInMyList(serie.id)"
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
               :src="film.imageUrl || require('../assets/default-poster.jpeg')"
               alt="Affiche"
               @error="
                  (event) =>
                     (event.target.src = require('../assets/default-poster.jpeg'))
               " />
            <div>
               <h3>{{ serie.titre }}</h3>
               <p>{{ serie.description }}</p>
               <p v-if="serie.anneeSortie">Année : {{ serie.anneeSortie }}</p>
            </div>
         </div>
      </div>
   </div>
</template>

<style scoped>
.serie-list {
   margin-top: 2rem;
}
.series {
   display: flex;
   gap: 2rem;
   flex-wrap: wrap;
   margin-top: 2rem;
}
.serie-card {
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
.serie-card img {
   width: 100%;
   height: 300px;
   object-fit: cover;
}
.serie-card h3 {
   margin: 0.5rem 1rem 0 1rem;
}
.serie-card p {
   margin: 0.5rem 1rem 1rem 1rem;
   font-size: 0.95rem;
   color: #444;
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
