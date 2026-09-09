<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { songs, ApiError, type SongDto } from '../api/api'
import SongCard from '../components/SongCard.vue'

const PAGE_SIZE = 20

const loading = ref(true)
const error = ref('')
const songList = ref<SongDto[]>([])
const currentPage = ref(0)
const totalPages = ref(0)
const totalItems = ref(0)

async function fetchSongs(page = currentPage.value) {
  loading.value = true
  error.value = ''
  try {
    const res = await songs.getAll(page, PAGE_SIZE)
    songList.value = res.items
    currentPage.value = res.page
    totalPages.value = res.totalPages
    totalItems.value = res.totalItems
  } catch (e) {
    error.value = e instanceof ApiError ? e.message : 'Failed to connect to server'
  } finally {
    loading.value = false
  }
}

function prevPage() {
  if (currentPage.value > 0) fetchSongs(currentPage.value - 1)
}

function nextPage() {
  if (currentPage.value < totalPages.value - 1) fetchSongs(currentPage.value + 1)
}

onMounted(() => fetchSongs(0))
</script>

<template>
  <div class="max-w-5xl mx-auto p-6">
    <h1 class="text-3xl font-heading font-bold mb-6">Songs</h1>

    <div v-if="loading" class="text-center py-12 text-gray-500">
      <div class="inline-block w-8 h-8 border-2 border-indigo-400/30 border-t-indigo-400 rounded-full animate-spin" />
      <p class="mt-3">Loading songs...</p>
    </div>

    <div v-else-if="error" class="text-center py-12">
      <p class="text-red-400 mb-4">{{ error }}</p>
      <button
        class="px-4 py-2 rounded bg-gray-700 hover:bg-gray-600 text-white cursor-pointer"
        @click="() => fetchSongs()"
      >
        Retry
      </button>
    </div>

    <div v-else-if="songList.length === 0" class="text-center py-12 text-gray-400">
      No songs found.
    </div>

    <div v-else>
      <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">
        <SongCard v-for="song in songList" :key="song.id" :song="song" />
      </div>

      <div class="flex items-center justify-between mt-8 pt-4 border-t border-gray-800/50">
        <p class="text-sm text-gray-500">
          {{ totalItems }} song{{ totalItems !== 1 ? 's' : '' }}
        </p>
        <div class="flex items-center gap-3">
          <button
            class="px-3 py-1.5 rounded text-sm font-medium transition-colors cursor-pointer
                   disabled:opacity-30 disabled:cursor-not-allowed
                   text-gray-400 hover:text-white hover:bg-gray-800"
            :disabled="currentPage === 0"
            @click="prevPage"
          >
            ← Previous
          </button>
          <span class="text-sm text-gray-500 tabular-nums">
            Page {{ currentPage + 1 }} of {{ totalPages }}
          </span>
          <button
            class="px-3 py-1.5 rounded text-sm font-medium transition-colors cursor-pointer
                   disabled:opacity-30 disabled:cursor-not-allowed
                   text-gray-400 hover:text-white hover:bg-gray-800"
            :disabled="currentPage >= totalPages - 1"
            @click="nextPage"
          >
            Next →
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
