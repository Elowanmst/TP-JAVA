<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const film = ref(null)
const commentaires = ref([])
const notes = ref([])

const commentaire = ref('')
const note = ref('')
const isSubmitting = ref(false)
const error = ref('')

// Charger les infos du film, commentaires et notes
async function fetchFilm() {
  const id = route.params.id
  const filmRes = await fetch(`http://localhost:8080/api/films/${id}`)
  film.value = await filmRes.json()
}
async function fetchCommentaires() {
  const id = route.params.id
  const res = await fetch(`http://localhost:8080/api/commentaires/film/${id}`)
  commentaires.value = await res.json()
}
async function fetchNotes() {
  const id = route.params.id
  const res = await fetch(`http://localhost:8080/api/notes/film/${id}`)
  notes.value = await res.json()
}

onMounted(async () => {
  await fetchFilm()
  await fetchCommentaires()
  await fetchNotes()
})

async function submitCommentaire() {
  isSubmitting.value = true
  error.value = ''
  try {
    const id = route.params.id
    const res = await fetch(`http://localhost:8080/api/commentaires/film/${id}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ contenu: commentaire.value })
    })
    if (!res.ok) throw new Error('Erreur lors de l\'ajout du commentaire')
    commentaire.value = ''
    await fetchCommentaires()
  } catch (e) {
    error.value = e.message
  } finally {
    isSubmitting.value = false
  }
}

async function submitNote() {
  isSubmitting.value = true
  error.value = ''
  try {
    const id = route.params.id
    const res = await fetch(`http://localhost:8080/api/notes/film/${id}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ valeur: parseInt(note.value, 10) })
    })
    if (!res.ok) throw new Error('Erreur lors de l\'ajout de la note')
    note.value = ''
    await fetchNotes()
  } catch (e) {
    error.value = e.message
  } finally {
    isSubmitting.value = false
  }
}
</script>

<template>
  <div class="film-detail" v-if="film">
    <h1>{{ film.titre }}</h1>
    <p><strong>Description :</strong> {{ film.description }}</p>
    <p><strong>Année de sortie :</strong> {{ film.anneeSortie }}</p>

    <div class="forms">
      <form @submit.prevent="submitCommentaire" class="comment-form">
        <h3>Ajouter un commentaire</h3>
        <textarea v-model="commentaire" placeholder="Votre commentaire..." required></textarea>
        <button type="submit" :disabled="isSubmitting">Envoyer</button>
      </form>

      <form @submit.prevent="submitNote" class="note-form">
        <h3>Ajouter une note</h3>
        <input type="number" v-model="note" min="0" max="20" placeholder="Note sur 20" required />
        <button type="submit" :disabled="isSubmitting">Noter</button>
      </form>
    </div>

    <div v-if="error" class="error">{{ error }}</div>

    <div class="commentaires">
      <h3>Commentaires</h3>
      <div v-if="commentaires.length === 0">Aucun commentaire.</div>
      <ul>
        <li v-for="c in commentaires" :key="c.id">
          {{ c.contenu }}
        </li>
      </ul>
    </div>

    <div class="notes">
      <h3>Notes</h3>
      <div v-if="notes.length === 0">Aucune note.</div>
      <ul>
        <li v-for="n in notes" :key="n.id">
          Note : {{ n.valeur }}/20
        </li>
      </ul>
    </div>
  </div>
  <div v-else>
    <p>Chargement...</p>
  </div>
</template>

<style scoped>
.film-detail {
  max-width: 600px;
  margin: 2rem auto;
  background: #fff;
  color: #222;
  border-radius: 10px;
  padding: 2rem;
  box-shadow: 0 2px 8px #0001;
  margin-top: 200px;
}
.forms {
  display: flex;
  gap: 2rem;
  margin-bottom: 2rem;
}
.comment-form, .note-form {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}
textarea, input[type="number"] {
  padding: 0.5em;
  border-radius: 4px;
  border: 1px solid #ccc;
}
button {
  background: #222;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 0.5em 1em;
  cursor: pointer;
}
button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
.error {
  color: #c00;
  margin-bottom: 1rem;
}
.commentaires, .notes {
  margin-top: 2rem;
}
</style>