import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../pages/HomePage.vue'
import SongList from '../pages/SongList.vue'
import AlbumView from '../pages/AlbumView.vue'
import ArtistView from '../pages/ArtistView.vue'

const routes = [
    { path: '/', component: HomePage },
    { path: '/songs', component: SongList },
    { path: '/albums/:id', component: AlbumView },
    { path: '/artists/:id', component: ArtistView }
]

export default createRouter({ history: createWebHistory(), routes })