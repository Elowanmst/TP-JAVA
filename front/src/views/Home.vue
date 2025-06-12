<script setup>
import MovieList from '../components/MovieList.vue'
</script>

<template>
  <div class="header-container">
    <header>
      <img alt="Logo" class="logo" src="../assets/logo-noir.png" width="225" height="225" />
      <h1>Bienvenue sur MovieTracker</h1>
      <p>Suivez vos films et séries préférés, ajoutez-les à votre liste et marquez votre progression !</p>
    </header>
    <div class="cta-links">
      <router-link to="/movies" class="cta">Voir les films</router-link>
      <router-link to="/series" class="cta">Voir les séries</router-link>
      <router-link to="/my-list" class="cta">Ma liste</router-link>
    </div>
    <MovieList />
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
  transition: background 0.2s, color 0.2s;
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
</style>