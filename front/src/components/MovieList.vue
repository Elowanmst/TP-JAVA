<script setup>
const movies = [
  { id: 1, title: 'Inception', description: 'Un voleur qui dérobe des secrets...', poster: 'https://image.tmdb.org/t/p/w200/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg' },
  { id: 2, title: 'Interstellar', description: 'Des explorateurs voyagent...', poster: 'https://image.tmdb.org/t/p/w200/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg' },
  { id: 3, title: 'The Dark Knight', description: 'Batman affronte le Joker...', poster: 'https://image.tmdb.org/t/p/w200/qJ2tW6WMUDux911r6m7haRef0WH.jpg' },
]
</script>

<template>
  <div class="movie-list">
    <h2>Films populaires</h2>
    <div class="movies">
      <div v-for="movie in movies" :key="movie.id" class="movie-card">
        <img :src="movie.poster" :alt="movie.title" />
        <div>
          <h3>{{ movie.title }}</h3>
          <p>{{ movie.description }}</p>
        </div>
      </div>
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
}
.movie-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px #0001;
  width: 200px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
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
</style>