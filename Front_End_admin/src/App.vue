<template>
  <div class="app">
    <Header />
    <main class="main-content">
      <RouterView />
    </main>
    <Footer />
  </div>
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

<style>
.app {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 화면 전체 높이를 채우도록 설정 */
}

.main-content {
  flex-grow: 1; /* 메인 콘텐츠가 남은 공간을 채움 */
  padding: 20px;
  background-color: #ffffff; /* 배경색 설정 */
}
</style>