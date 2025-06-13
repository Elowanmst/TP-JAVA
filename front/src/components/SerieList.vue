<script setup>
import { ref, onMounted } from 'vue'

const series = ref([])

async function fetchSeries() {
  try {
    const response = await fetch('http://localhost:8080/api/series')
    series.value = await response.json()
  } catch (e) {
    series.value = []
  }
}

onMounted(fetchSeries)

defineExpose({ fetchSeries })
</script>

<template>
  <div class="serie-list">
    <h2>Séries populaires</h2>
    <div class="series">
      <div v-for="serie in series" :key="serie.id" class="serie-card">
        <img src="https://image.tmdb.org/t/p/w200/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg" alt="Affiche" />
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
</style>