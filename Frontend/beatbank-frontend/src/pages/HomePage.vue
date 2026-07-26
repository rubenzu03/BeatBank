<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { songs, artists, albums, genres, ApiError, type SongDto, type AlbumDto, type GenreDto } from '../api/api'

const router = useRouter()

const loading = ref(true)
const error = ref('')

const songCount = ref(0)
const artistCount = ref(0)
const albumCount = ref(0)
const topSongs = ref<SongDto[]>([])
const latestAlbums = ref<AlbumDto[]>([])
const genreList = ref<GenreDto[]>([])

async function fetchDashboard() {
  loading.value = true
  error.value = ''
  try {
    const [songsRes, artistsRes, albumsRes, topRes, albumRes, genreRes] = await Promise.all([
      songs.getAll(0, 1),
      artists.getAll(0, 1),
      albums.getAll(0, 1),
      songs.getAll(0, 5, 'plays,desc'),
      albums.getAll(0, 5, 'releaseDate,desc'),
      genres.getAll(0, 50),
    ])

    songCount.value = songsRes.totalItems
    artistCount.value = artistsRes.totalItems
    albumCount.value = albumsRes.totalItems
    topSongs.value = topRes.items
    latestAlbums.value = albumRes.items
    genreList.value = genreRes.items
  } catch (e) {
    error.value = e instanceof ApiError ? e.message : 'Failed to connect to server'
  } finally {
    loading.value = false
  }
}

onMounted(fetchDashboard)
</script>

<template>
  <div class="max-w-5xl mx-auto p-6">
    <div v-if="loading" class="space-y-8">
      <div class="h-12 w-48 rounded-lg bg-gray-900/50 animate-pulse" />
      <div class="grid grid-cols-2 gap-4">
        <div class="h-64 rounded-xl bg-gray-900/50 backdrop-blur-xl animate-pulse" />
        <div class="h-64 rounded-xl bg-gray-900/50 backdrop-blur-xl animate-pulse" />
      </div>
      <div class="h-20 rounded-xl bg-gray-900/50 backdrop-blur-xl animate-pulse" />
    </div>

    <div v-else-if="error" class="text-center py-12">
      <p class="text-red-400 mb-4">{{ error }}</p>
      <button
        class="px-4 py-2 rounded bg-gray-700 hover:bg-gray-600 text-white cursor-pointer"
        @click="fetchDashboard"
      >
        Retry
      </button>
    </div>

    <template v-else>
      <div class="flex items-center justify-between mb-8">
        <h1 class="text-3xl font-heading font-bold text-white">Dashboard</h1>
        <div class="flex items-center gap-3">
          <div class="flex items-center gap-2 px-3 py-1.5 rounded-full bg-gray-900/50 backdrop-blur-xl shadow-lg shadow-black/20">
            <span class="text-sm font-semibold text-indigo-400 tabular-nums">{{ songCount }}</span>
            <span class="text-sm text-gray-500">songs</span>
          </div>
          <div class="flex items-center gap-2 px-3 py-1.5 rounded-full bg-gray-900/50 backdrop-blur-xl shadow-lg shadow-black/20">
            <span class="text-sm font-semibold text-indigo-400 tabular-nums">{{ artistCount }}</span>
            <span class="text-sm text-gray-500">artists</span>
          </div>
          <div class="flex items-center gap-2 px-3 py-1.5 rounded-full bg-gray-900/50 backdrop-blur-xl shadow-lg shadow-black/20">
            <span class="text-sm font-semibold text-indigo-400 tabular-nums">{{ albumCount }}</span>
            <span class="text-sm text-gray-500">albums</span>
          </div>
        </div>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8">
        <div class="rounded-xl bg-gray-900/50 backdrop-blur-xl shadow-lg shadow-black/20 overflow-hidden">
          <div class="h-0.5 bg-gradient-to-r from-indigo-400/30 to-violet-400/30 shrink-0" />
          <div class="p-5">
            <div class="flex items-center justify-between mb-4">
            <h2 class="text-lg font-heading font-bold text-white">Top Songs</h2>
            <button
              class="text-sm text-indigo-400 hover:text-indigo-300 cursor-pointer transition-colors"
              @click="router.push('/songs')"
            >
              View all →
            </button>
          </div>
          <div v-if="topSongs.length === 0" class="text-sm text-gray-500 py-4 text-center">
            No songs yet.
          </div>
          <div v-else class="space-y-2">
            <div
              v-for="(song, i) in topSongs"
              :key="song.id"
              class="flex items-center gap-3 py-2 border-b border-gray-700/50 last:border-0"
            >
              <span class="text-sm font-mono text-gray-500 w-5">{{ i + 1 }}</span>
              <div class="flex-1 min-w-0">
                <p class="text-sm font-medium text-white truncate">{{ song.name }}</p>
                <p class="text-xs text-gray-400 truncate">
                  <template v-if="song.artists.length > 0">
                    <template v-for="(artist, ai) in song.artists" :key="artist.id">
                      <button
                         class="hover:text-indigo-400 transition-colors cursor-pointer"
                        @click.stop="router.push(`/artists/${artist.id}`)"
                      >
                        {{ artist.name }}
                      </button>{{ ai < song.artists.length - 1 ? ', ' : '' }}
                    </template>
                  </template>
                  <template v-else>—</template>
                </p>
              </div>
              <div class="text-right">
                <span class="text-xs text-gray-500 tabular-nums">{{ song.duration }}</span>
                <p class="text-xs text-gray-600 tabular-nums">{{ song.plays?.toLocaleString() ?? 0 }}</p>
              </div>
            </div>
          </div>
        </div>
        </div>

        <div class="rounded-xl bg-gray-900/50 backdrop-blur-xl shadow-lg shadow-black/20 overflow-hidden">
          <div class="h-0.5 bg-gradient-to-r from-indigo-400/30 to-violet-400/30 shrink-0" />
          <div class="p-5">
            <div class="flex items-center justify-between mb-4">
            <h2 class="text-lg font-heading font-bold text-white">Latest Albums</h2>
            <button
              class="text-sm text-indigo-400 hover:text-indigo-300 cursor-pointer transition-colors"
              @click="router.push('/songs')"
            >
              View all →
            </button>
          </div>
          <div v-if="latestAlbums.length === 0" class="text-sm text-gray-500 py-4 text-center">
            No albums yet.
          </div>
          <div v-else class="grid grid-cols-2 sm:grid-cols-3 gap-3">
            <div
              v-for="album in latestAlbums"
              :key="album.id"
              class="text-center group cursor-pointer"
              @click="router.push(`/albums/${album.id}`)"
            >
              <div class="aspect-square rounded-lg bg-gray-950 mb-2 overflow-hidden shadow-lg shadow-black/30 group-hover:ring-2 group-hover:ring-indigo-500/40 transition-all">
                <img
                  v-if="album.coverImageUrl"
                  :src="album.coverImageUrl"
                  :alt="album.name"
                  class="w-full h-full object-cover"
                />
                <div v-else class="w-full h-full flex items-center justify-center text-gray-700">
                  <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                          d="M9 19V6l12-3v13M9 19c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zm12-3c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zM9 10l12-3" />
                  </svg>
                </div>
              </div>
              <p class="text-sm font-medium text-white truncate">{{ album.name }}</p>
              <p v-if="album.genre" class="text-xs text-gray-500">{{ album.genre.name }}</p>
              <p class="text-xs text-gray-600">{{ album.releaseDate?.slice(0, 4) || '—' }}</p>
            </div>
          </div>
        </div>
        </div>
      </div>

      <div v-if="genreList.length > 0" class="rounded-xl bg-gray-900/50 backdrop-blur-xl shadow-lg shadow-black/20 overflow-hidden">
        <div class="h-0.5 bg-gradient-to-r from-indigo-400/30 to-violet-400/30 shrink-0" />
        <div class="p-5">
          <h2 class="text-lg font-heading font-bold text-white mb-4">Browse by Genre</h2>
          <div class="flex flex-wrap gap-2">
            <button
              v-for="genre in genreList"
              :key="genre.id"
                class="px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200 cursor-pointer
                       bg-gray-800/50 text-gray-400 hover:bg-indigo-500/10 hover:text-indigo-300 hover:shadow-lg hover:shadow-indigo-500/5"
              @click="router.push(`/songs?genre=${genre.id}`)"
            >
              {{ genre.name }}
            </button>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>
