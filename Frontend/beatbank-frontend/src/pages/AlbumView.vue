<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { albums, ApiError, type AlbumDto, type SongDto } from '../api/api'

const route = useRoute()
const router = useRouter()

const loading = ref(true)
const error = ref('')
const album = ref<AlbumDto | null>(null)
const songList = ref<SongDto[]>([])

async function fetchAlbum() {
  loading.value = true
  error.value = ''
  try {
    const id = Number(route.params.id)
    const [albumRes, songsRes] = await Promise.all([
      albums.getById(id),
      albums.getSongs(id, 0, 100),
    ])
    album.value = albumRes
    songList.value = songsRes.items
  } catch (e) {
    error.value = e instanceof ApiError ? e.message : 'Failed to connect to server'
  } finally {
    loading.value = false
  }
}

const mainArtist = computed(() => {
  const counts = new Map<number, { id: number; name: string; count: number }>()
  for (const song of songList.value) {
    for (const artist of song.artists) {
      const entry = counts.get(artist.id)
      if (entry) {
        entry.count++
      } else {
        counts.set(artist.id, { id: artist.id, name: artist.name, count: 1 })
      }
    }
  }
  let best: { id: number; name: string } | null = null
  let maxCount = 0
  for (const entry of counts.values()) {
    if (entry.count > maxCount) {
      best = { id: entry.id, name: entry.name }
      maxCount = entry.count
    }
  }
  return best
})

const totalDuration = computed(() => {
  let totalSeconds = 0
  for (const song of songList.value) {
    const parts = song.duration.split(':')
    if (parts.length === 2) {
      totalSeconds += parseInt(parts[0]) * 60 + parseInt(parts[1])
    } else if (parts.length === 3) {
      totalSeconds += parseInt(parts[0]) * 3600 + parseInt(parts[1]) * 60 + parseInt(parts[2])
    }
  }
  const hours = Math.floor(totalSeconds / 3600)
  const minutes = Math.floor((totalSeconds % 3600) / 60)
  if (hours > 0) return `${hours}h ${minutes}m`
  return `${minutes} min`
})

const totalPlays = computed(() => {
  return songList.value.reduce((sum, s) => sum + (s.plays ?? 0), 0)
})

onMounted(fetchAlbum)
</script>

<template>
  <div class="max-w-4xl mx-auto p-6">
    <button
      class="text-sm text-gray-500 hover:text-white mb-6 cursor-pointer transition-colors"
      @click="router.back()"
    >
      ← Back
    </button>

    <div v-if="loading" class="flex gap-8">
      <div class="w-64 h-64 rounded-xl bg-gray-900/50 backdrop-blur-xl animate-pulse shrink-0" />
      <div class="flex-1 space-y-4">
        <div class="h-8 w-48 bg-gray-900/50 backdrop-blur-xl rounded animate-pulse" />
        <div class="h-4 w-32 bg-gray-900/50 backdrop-blur-xl rounded animate-pulse" />
        <div class="h-4 w-64 bg-gray-900/50 backdrop-blur-xl rounded animate-pulse" />
      </div>
    </div>

    <div v-else-if="error" class="text-center py-12">
      <p class="text-red-400 mb-4">{{ error }}</p>
      <button
        class="px-4 py-2 rounded bg-gray-700 hover:bg-gray-600 text-white cursor-pointer"
        @click="fetchAlbum"
      >
        Retry
      </button>
    </div>

    <template v-else-if="album">
      <div class="relative overflow-hidden rounded-2xl mb-8">
        <div
          v-if="album.coverImageUrl"
          class="absolute inset-0 bg-cover bg-center blur-3xl opacity-30 scale-110"
          :style="{ backgroundImage: `url(${album.coverImageUrl})` }"
        />
        <div
          class="absolute inset-0"
          :class="album.coverImageUrl
            ? 'bg-gradient-to-b from-transparent via-gray-950/60 to-gray-950'
            : 'bg-gradient-to-br from-gray-900 to-gray-950'"
        />

        <div class="relative z-10 flex flex-col sm:flex-row gap-6 sm:gap-10 p-6 sm:p-8">
          <div class="w-40 h-40 sm:w-56 sm:h-56 rounded-2xl overflow-hidden shrink-0 shadow-2xl shadow-black/50 mx-auto sm:mx-0">
            <img
              v-if="album.coverImageUrl"
              :src="album.coverImageUrl"
              :alt="album.name"
              class="w-full h-full object-cover"
            />
            <div v-else class="w-full h-full flex items-center justify-center bg-gray-800 text-gray-600">
              <svg class="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                      d="M9 19V6l12-3v13M9 19c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zm12-3c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zM9 10l12-3" />
              </svg>
            </div>
          </div>

          <div class="flex flex-col justify-end text-center sm:text-left">
            <p v-if="album.genre" class="text-xs font-semibold text-indigo-400 uppercase tracking-widest mb-2">
              {{ album.genre.name }}
            </p>
            <h1 class="text-2xl sm:text-4xl font-bold text-white mb-2 leading-tight">{{ album.name }}</h1>
            <p v-if="mainArtist" class="text-base text-gray-300 mb-2">
              <button
                class="font-medium hover:text-white transition-colors cursor-pointer"
                @click="router.push(`/artists/${mainArtist.id}`)"
              >
                {{ mainArtist.name }}
              </button>
            </p>
            <div class="flex flex-wrap gap-x-3 gap-y-1 justify-center sm:justify-start text-sm text-gray-500">
              <span>{{ album.releaseDate?.slice(0, 4) || '—' }}</span>
              <span class="text-gray-700">·</span>
              <span>{{ songList.length }} tracks</span>
              <span class="text-gray-700">·</span>
              <span>{{ totalDuration }}</span>
            </div>
            <p v-if="album.description" class="text-sm text-gray-400 mt-4 max-w-lg leading-relaxed">
              {{ album.description }}
            </p>
          </div>
        </div>
      </div>

      <div class="bg-gray-900/50 backdrop-blur-xl shadow-lg shadow-black/20 rounded-2xl overflow-hidden">
        <div class="h-0.5 bg-gradient-to-r from-indigo-400/30 to-violet-400/30 shrink-0" />
        <div class="p-6 sm:p-8">
          <div class="flex items-center justify-between mb-6">
          <h2 class="text-lg font-heading font-bold text-white">
            Tracklist
            <span class="text-base font-normal text-gray-500">({{ songList.length }})</span>
          </h2>
          <p class="text-sm text-gray-500 tabular-nums">{{ totalPlays.toLocaleString() }} total plays</p>
        </div>

        <div v-if="songList.length === 0" class="text-sm text-gray-500 py-8 text-center">
          No songs in this album yet.
        </div>

        <div v-else class="space-y-0.5">
          <div
            v-for="(song, i) in songList"
            :key="song.id"
            class="group flex items-center gap-4 px-4 py-2.5 rounded-xl transition-colors hover:bg-white/5"
          >
            <span class="text-sm font-mono text-gray-600 w-6 text-right group-hover:hidden">{{ i + 1 }}</span>
            <svg class="w-3.5 h-3.5 text-gray-400 hidden group-hover:block shrink-0 ml-1" fill="currentColor" viewBox="0 0 16 16">
              <path d="M4 2v12l10-6z" />
            </svg>
            <div class="flex-1 min-w-0">
              <p class="text-sm font-medium text-white truncate">{{ song.name }}</p>
              <p class="text-xs text-gray-500 truncate">
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
              </p>
            </div>
            <span class="text-sm text-gray-500 tabular-nums">{{ song.duration }}</span>
            <span class="text-sm text-gray-600 tabular-nums w-20 text-right hidden sm:block">{{ song.plays?.toLocaleString() ?? 0 }}</span>
          </div>
        </div>
        </div>
      </div>
    </template>
  </div>
</template>
