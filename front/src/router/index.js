import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Movies from '../views/Movies.vue'
import Series from '../views/Series.vue'
import MyList from '../views/MyList.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import FilmDetail from '../views/FilmDetail.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/movies', component: Movies },
  { path: '/series', component: Series },
  { path: '/my-list', component: MyList },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/films/:id', component: FilmDetail },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router