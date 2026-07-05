<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { songs, ApiError, type SongDto } from '../api/api'

const loading = ref(true)
const error = ref('')
const songList = ref<SongDto[]>([])

async function fetchSongs() {
  loading.value = true
  error.value = ''
  try {
    const res = await songs.getAll(0, 20)
    songList.value = res.items
  } catch (e) {
    error.value = e instanceof ApiError ? e.message : 'Failed to connect to server'
  } finally {
    loading.value = false
  }
}

onMounted(fetchSongs)
</script>

<template>
  <div class="max-w-5xl mx-auto p-6">
    <h1 class="text-3xl font-bold mb-6">Songs</h1>

    <div v-if="loading" class="text-center py-12 text-gray-400">
      <div class="inline-block w-8 h-8 border-2 border-gray-400 border-t-transparent rounded-full animate-spin" />
      <p class="mt-3">Loading songs...</p>
    </div>

    <div v-else-if="error" class="text-center py-12">
      <p class="text-red-400 mb-4">{{ error }}</p>
      <button
        class="px-4 py-2 rounded bg-gray-700 hover:bg-gray-600 text-white cursor-pointer"
        @click="fetchSongs"
      >
        Retry
      </button>
    </div>

    <div v-else-if="songList.length === 0" class="text-center py-12 text-gray-400">
      No songs found.
    </div>

    <div v-else class="overflow-x-auto">
      <table class="w-full text-left border-collapse">
        <thead>
          <tr class="border-b border-gray-700 text-gray-400 text-sm uppercase">
            <th class="py-3 pr-4">Title</th>
            <th class="py-3 pr-4">Artist(s)</th>
            <th class="py-3 pr-4">Album</th>
            <th class="py-3 pr-4">Duration</th>
            <th class="py-3 text-right">Plays</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="song in songList"
            :key="song.id"
            class="border-b border-gray-800 hover:bg-gray-800/40 transition-colors"
          >
            <td class="py-3 pr-4 font-medium">{{ song.name }}</td>
            <td class="py-3 pr-4 text-gray-400">
              {{ song.artists.map(a => a.name).join(', ') || '—' }}
            </td>
            <td class="py-3 pr-4 text-gray-400">{{ song.album?.name ?? '—' }}</td>
            <td class="py-3 pr-4 text-gray-400">{{ song.duration }}</td>
            <td class="py-3 text-right text-gray-400">{{ song.plays?.toLocaleString() ?? 0 }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
