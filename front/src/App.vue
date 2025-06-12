<script setup>
import Navbar from './components/Navbar.vue'
</script>

<template>
  <div>
    <Navbar />
    <router-view />
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
</style>