<script setup>
import Navbar from './components/Navbar.vue'
</script>

<template>
  <div>
    <Navbar />
    <router-view />
    <footer class="footer">
      <div>
        © 2025 MovieTracker — Elowan et Anthony.
      </div>
    </footer>
  </div>
</template>

<style scoped>
:deep(.navbar) {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  z-index: 1000;
}

.footer {
  width: 100vw;
  background: #222;
  color: #fff;
  text-align: center;
  padding: 1.2rem 0;
  margin-top: 2rem;
  font-size: 1rem;
  position: relative;
  bottom: 0;
  left: 0;
}
</style>