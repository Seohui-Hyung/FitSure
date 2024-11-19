<template>
  <header>
    <nav>
      <RouterLink to="/">Home</RouterLink> |
      <RouterLink v-if="!isAuthenticated" to="/login">로그인</RouterLink>
      <button v-else="isAuthenticated" @click="logout">로그아웃</button> |
      <RouterLink v-if="!isAuthenticated" to="/signup">회원가입</RouterLink> |
      <RouterLink v-if="isAuthenticated" to="/profile">프로필</RouterLink>
    </nav>
  </header>
  <RouterView />
</template>

<script setup>
import { computed } from 'vue'
import { useRouter, RouterView, RouterLink } from 'vue-router'

const router = useRouter()
const isAuthenticated = computed(() => localStorage.getItem('auth') === 'true')

function logout() {
  localStorage.removeItem('auth')
  alert('로그아웃 되었습니다.')
  router.push({ name: 'home' })
}
</script>
