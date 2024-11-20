<template>
    <div>
      <h2>로그인</h2>
      <form @submit.prevent="login">
        <label for="username">아이디</label>
        <input id="username" v-model="username" type="text" placeholder="아이디 입력" required />
  
        <label for="password">비밀번호</label>
        <input id="password" v-model="password" type="password" placeholder="비밀번호 입력" required />
  
        <button type="submit">로그인</button>
      </form>

      <p v-if="errorMessage" style="color: red">{{ errorMessage }}</p>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router'
  import { ref } from 'vue';
  
  const router = useRouter()
  const username = ref('')
  const password = ref('')
  const errorMessage = ref('')
  
  function login() {
    // 간단한 인증 시뮬레이션
    if (username.value === 'user' && password.value === 'password') {
      localStorage.setItem('auth', 'true') // 인증 상태 저장
      alert('로그인 성공!')
      router.push({ name: 'profile' }) // 프로필 화면으로 이동
    } else {
      errorMessage.value = '아이디 또는 비밀번호가 잘못되었습니다.'
    }
  }
  </script>
  