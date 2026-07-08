import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../pages/HomePage.vue'
import SongList from '../pages/SongList.vue'

const routes = [
    { path: '/', component: HomePage },
    { path: '/songs', component: SongList }
]

export default createRouter({ history: createWebHistory(), routes })