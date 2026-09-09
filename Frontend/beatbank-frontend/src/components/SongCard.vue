<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import type { SongDto } from '../api/api'

const router = useRouter()

const props = defineProps<{
  song: SongDto
}>()

function formatDuration(duration: string): string {
  if (!duration) return '—'
  const parts = duration.split(':')
  if (parts.length === 3) {
    const [h, m, s] = parts
    return `${h}:${m.padStart(2, '0')}:${s.padStart(2, '0')}`
  }
  return duration
}

const coverSrc = computed(() => {
  return props.song.album?.coverImageUrl || null
})
</script>

<template>
  <div
    class="rounded-xl bg-gray-900/50 backdrop-blur-xl shadow-lg shadow-black/20 overflow-hidden
           hover:bg-gray-800/50 hover:shadow-xl hover:shadow-indigo-500/5 hover:-translate-y-0.5
           transition-all duration-300 flex flex-col group cursor-pointer"
  >
    <div class="h-0.5 bg-gradient-to-r from-indigo-400/30 to-violet-400/30 shrink-0" />
    <div class="aspect-square relative overflow-hidden bg-gray-950">
      <img
        v-if="coverSrc"
        :src="coverSrc"
        :alt="`${song.album?.name} cover`"
        class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
      />
      <div
        v-else
        class="w-full h-full flex items-center justify-center text-gray-600"
      >
        <svg class="w-12 h-12" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                d="M9 19V6l12-3v13M9 19c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zm12-3c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zM9 10l12-3" />
        </svg>
      </div>
    </div>

    <div class="p-3 flex flex-col gap-1 flex-1">
      <p class="font-semibold text-white truncate text-sm">{{ song.name }}</p>
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
        <template v-if="song.album">
          <span class="mx-1">•</span>{{ song.album.name }}
        </template>
      </p>
      <div class="flex items-center justify-between mt-auto pt-1">
        <p class="text-xs text-gray-500">{{ formatDuration(song.duration) }}</p>
        <p class="text-xs text-gray-600 tabular-nums">{{ song.plays?.toLocaleString() ?? 0 }}</p>
      </div>
    </div>
  </div>
</template>
