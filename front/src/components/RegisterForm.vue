<template>
   <form @submit.prevent="register">
      <h2>Inscription</h2>
      <input v-model="username" placeholder="Nom d'utilisateur" required />
      <input
         v-model="password"
         type="password"
         placeholder="Mot de passe"
         required />
      <button type="submit">S'inscrire</button>
      <p v-if="error" style="color: red">{{ error }}</p>
      <p v-if="success" style="color: green">
         Inscription réussie, connectez-vous !
      </p>
   </form>
</template>

<script setup>
import { ref } from "vue";

const username = ref("");
const password = ref("");
const error = ref("");
const success = ref(false);

async function register() {
   error.value = "";
   success.value = false;
   try {
      const res = await fetch(
         "http://localhost:8080/api/utilisateurs/register",
         {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
               username: username.value,
               password: password.value,
            }),
         }
      );
      if (!res.ok) throw new Error("Nom d'utilisateur déjà pris");
      success.value = true;
      username.value = "";
      password.value = "";
   } catch (e) {
      error.value = e.message;
   }
}
</script>

<style scoped>
form {
   background: #fff;
   color: #222;
   border-radius: 8px;
   box-shadow: 0 2px 8px #0002;
   padding: 2rem 1.5rem;
   max-width: 350px;
   margin: 2rem auto;
   display: flex;
   flex-direction: column;
   gap: 1rem;
}
input {
   padding: 0.5em;
   border: 1px solid #ccc;
   border-radius: 4px;
   font-size: 1rem;
}
button {
   background: #222;
   color: #fff;
   border: none;
   border-radius: 4px;
   padding: 0.6em 1.2em;
   font-size: 1rem;
   cursor: pointer;
   transition: background 0.2s;
}
button:hover {
   background: #444;
}
</style>
