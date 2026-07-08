<script setup lang="ts">
import AppHeader from './components/AppHeader.vue'
</script>

<template>
  <AppHeader />
  <main class="max-w-7xl mx-auto p-6">
    <router-view />
  </main>
</template>
