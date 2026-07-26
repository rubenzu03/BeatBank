<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'

const route = useRoute()
const router = useRouter()

const navLinks = [
  { name: 'Home', path: '/' },
  { name: 'Songs', path: '/songs' }
]

function isActive(path: string) {
  return route.path === path
}
</script>

<template>
  <header class="border-b border-gray-800/50 bg-gray-950/90 backdrop-blur-xl sticky top-0 z-50">
    <div class="max-w-7xl mx-auto flex items-center justify-between px-4 h-16">
      <button
        class="text-xl font-heading font-bold tracking-tight text-white"
        @click="router.push('/')"
      >
        BeatBank
      </button>
      <nav class="flex items-center gap-1">
        <button
          v-for="link in navLinks"
          :key="link.path"
          @click="router.push(link.path)"
          class="px-4 py-2 rounded-lg text-sm font-medium transition-colors"
          :class="isActive(link.path)
            ? 'bg-indigo-500/10 text-indigo-400'
            : 'text-gray-400 hover:text-white hover:bg-gray-800/50'"
        >
          {{ link.name }}
        </button>
      </nav>
    </div>
  </header>
</template>
