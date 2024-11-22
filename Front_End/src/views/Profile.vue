<template>
    <div>
      <h2>프로필</h2>
      <p>환영합니다, 사용자님!</p>
      <button @click="logout">로그아웃</button>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  function logout() {
    localStorage.removeItem('access-token') // 인증 상태 제거
    router.push({ name: 'login' }) // 로그인 화면으로 리다이렉트
  }
  </script>
  