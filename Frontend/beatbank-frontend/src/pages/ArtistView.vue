<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { artists, ApiError, type ArtistDto } from '../api/api'

const route = useRoute()
const router = useRouter()

const loading = ref(true)
const error = ref('')
const artist = ref<ArtistDto | null>(null)

async function fetchArtist() {
  loading.value = true
  error.value = ''
  try {
    const id = Number(route.params.id)
    artist.value = await artists.getById(id)
  } catch (e) {
    error.value = e instanceof ApiError ? e.message : 'Failed to connect to server'
  } finally {
    loading.value = false
  }
}

function hashString(str: string): number {
  let hash = 0
  for (let i = 0; i < str.length; i++) {
    hash = str.charCodeAt(i) + ((hash << 5) - hash)
  }
  return Math.abs(hash)
}

const gradientStyle = computed(() => {
  if (!artist.value) return {}
  const h = hashString(artist.value.name) % 360
  const h2 = (h + 35) % 360
  return {
    backgroundImage: `linear-gradient(135deg, hsl(${h}, 50%, 12%), hsl(${h2}, 45%, 6%))`,
  }
})

const initial = computed(() => {
  if (!artist.value) return '?'
  return artist.value.name.charAt(0).toUpperCase()
})

const totalPlays = computed(() => {
  if (!artist.value) return 0
  return artist.value.songs.reduce((sum, s) => sum + (s.plays ?? 0), 0)
})

onMounted(fetchArtist)
</script>

<template>
  <div class="max-w-4xl mx-auto p-6">
    <button
      class="text-sm text-gray-500 hover:text-white mb-6 cursor-pointer transition-colors"
      @click="router.back()"
    >
      ← Back
    </button>

    <div v-if="loading" class="space-y-4">
      <div class="h-10 w-64 bg-gray-900/50 backdrop-blur-xl rounded animate-pulse" />
      <div class="h-4 w-full bg-gray-900/50 backdrop-blur-xl rounded animate-pulse" />
      <div class="h-4 w-3/4 bg-gray-900/50 backdrop-blur-xl rounded animate-pulse" />
    </div>

    <div v-else-if="error" class="text-center py-12">
      <p class="text-red-400 mb-4">{{ error }}</p>
      <button
        class="px-4 py-2 rounded bg-gray-700 hover:bg-gray-600 text-white cursor-pointer"
        @click="fetchArtist"
      >
        Retry
      </button>
    </div>

    <template v-else-if="artist">
      <div
        class="relative overflow-hidden rounded-2xl mb-8 p-8 sm:p-10"
        :style="gradientStyle"
      >
        <div class="absolute inset-0 opacity-10">
          <div class="absolute -top-20 -right-20 w-72 h-72 rounded-full bg-white blur-3xl" />
          <div class="absolute -bottom-20 -left-20 w-72 h-72 rounded-full bg-white blur-3xl" />
        </div>

        <div class="relative z-10 flex flex-col sm:flex-row items-center sm:items-end gap-6">
          <div
            class="w-24 h-24 sm:w-32 sm:h-32 rounded-full shrink-0 overflow-hidden ring-4 ring-white/20
                   bg-white/10 backdrop-blur-sm flex items-center justify-center"
            :class="{ 'ring-0': artist.imageUrl }"
          >
            <img
              v-if="artist.imageUrl"
              :src="artist.imageUrl"
              :alt="artist.name"
              class="w-full h-full object-cover"
            />
            <span v-else class="text-4xl sm:text-5xl text-white font-bold">{{ initial }}</span>
          </div>
          <div class="text-center sm:text-left">
            <h1 class="text-3xl sm:text-5xl font-bold text-white mb-2">{{ artist.name }}</h1>
            <p v-if="artist.description" class="text-sm text-gray-300/80 max-w-xl leading-relaxed">
              {{ artist.description }}
            </p>
            <div class="flex flex-wrap gap-x-4 gap-y-1 mt-4 justify-center sm:justify-start text-sm">
              <span class="text-white/70">
                <strong class="text-white font-semibold">{{ artist.songs.length }}</strong> songs
              </span>
              <span class="text-white/30">·</span>
              <span class="text-white/70">
                <strong class="text-white font-semibold">{{ totalPlays.toLocaleString() }}</strong> total plays
              </span>
            </div>
          </div>
        </div>
      </div>

      <div class="bg-gray-900/50 backdrop-blur-xl shadow-lg shadow-black/20 rounded-2xl overflow-hidden">
        <div class="h-0.5 bg-gradient-to-r from-indigo-400/30 to-violet-400/30 shrink-0" />
        <div class="p-6 sm:p-8">
          <h2 class="text-lg font-heading font-bold text-white mb-6">
          Songs
          <span class="text-base font-normal text-gray-500">({{ artist.songs.length }})</span>
        </h2>

        <div v-if="artist.songs.length === 0" class="text-sm text-gray-500 py-8 text-center">
          No songs found for this artist.
        </div>

        <div v-else class="space-y-0.5">
          <div
            v-for="(song, i) in artist.songs"
            :key="song.id"
            class="group flex items-center gap-4 px-4 py-2.5 rounded-xl transition-colors hover:bg-white/5"
          >
            <span class="text-sm font-mono text-gray-600 w-6 text-right group-hover:hidden">{{ i + 1 }}</span>
            <svg class="w-3.5 h-3.5 text-gray-400 hidden group-hover:block shrink-0 ml-1" fill="currentColor" viewBox="0 0 16 16">
              <path d="M4 2v12l10-6z" />
            </svg>
            <div class="flex-1 min-w-0">
              <p class="text-sm font-medium text-white truncate">{{ song.name }}</p>
            </div>
            <span class="text-sm text-gray-500 tabular-nums">{{ song.duration }}</span>
            <span class="text-sm text-gray-600 tabular-nums w-20 text-right hidden sm:block">
              {{ song.plays?.toLocaleString() ?? 0 }}
            </span>
          </div>
        </div>
        </div>
      </div>
    </template>
  </div>
</template>
