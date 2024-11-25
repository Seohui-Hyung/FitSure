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
import Header from './components/common/Header.vue'
import Footer from './components/common/Footer.vue'
// import Main from './views/Main.vue'

const router = useRouter()
const isAuthenticated = computed(() => localStorage.getItem('auth') === 'true')

// export default {
//   components: {
//     Header,
//     Footer,
//   }
// }

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

div {
  margin-bottom: 0px;
}

.main-content {
  flex-grow: 1; /* 메인 콘텐츠가 남은 공간을 채움 */
  background-color: #ffffff; /* 배경색 설정 */
}

footer {
  background-color: #043873;
  color: white;
  text-align: center;
  padding: 20px 0;
}

@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'GmarketSansMedium', Arial, Helvetica, sans-serif;
}
</style>