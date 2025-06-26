<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const username = ref("");
const router = useRouter();

function logout() {
   localStorage.removeItem("userId");
   localStorage.removeItem("username");
   router.push("/login");
   window.location.reload();
}

onMounted(() => {
   username.value = localStorage.getItem("username") || "";
});
</script>

<template>
   <nav class="navbar">
      <div class="navbar-content">
         <div class="logo">
            <img
               src="../assets/logo-noir.png"
               alt="Logo"
               width="80"
               height="80" />
         </div>
         <ul>
            <li><router-link to="/">Accueil</router-link></li>
            <li><router-link to="/movies">Films</router-link></li>
            <li><router-link to="/series">Séries</router-link></li>
            <li><router-link to="/my-list">Ma liste</router-link></li>
            <template v-if="username">
               <li style="color: #fff; font-weight: bold">👤 {{ username }}</li>
               <li>
                  <button @click="logout" class="cta" style="cursor: pointer">
                     Déconnexion
                  </button>
               </li>
            </template>
            <template v-else>
               <li>
                  <router-link to="/login" class="cta">Connexion</router-link>
               </li>
               <li>
                  <router-link to="/register" class="cta"
                     >Inscription</router-link
                  >
               </li>
            </template>
         </ul>
      </div>
   </nav>
</template>

<style scoped>
.navbar {
   background: #222;
   padding: 1rem 2rem;
   width: 100%;
}
.navbar-content {
   display: flex;
   align-items: center;
   justify-content: space-between;
}
.logo img {
   display: block;
}
.navbar ul {
   display: flex;
   gap: 2rem;
   list-style: none;
   margin: 0;
   padding: 0;
}
.navbar a {
   color: #fff;
   text-decoration: none;
   font-weight: bold;
   border-radius: 4px;
   transition:
      background 0.2s,
      color 0.2s;
}
.navbar a:hover {
   text-decoration: underline;
}
.navbar a.cta {
   background: #fff;
   color: #222;
   padding: 0.4em 1em;
   text-decoration: none;
   font-weight: bold;
   border-radius: 4px;
}
.navbar a.cta:hover {
   background: #eee;
   color: #111;
   text-decoration: none;
}
</style>
