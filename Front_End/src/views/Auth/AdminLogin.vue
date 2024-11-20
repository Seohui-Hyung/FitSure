<template>
    <div>
      <h2>관리자 로그인</h2>
      <form @submit.prevent="login">
        <label for="adminId">관리자 아이디</label>
        <input id="adminId" v-model="adminId" type="text" placeholder="아이디 입력" required />
    
        <label for="adminPassword">비밀번호</label>
        <input id="adminPassword" v-model="adminPassword" type="password" placeholder="비밀번호 입력" required />
    
        <button type="submit">로그인</button>
      </form>
  
      <p v-if="errorMessage" style="color: red">{{ errorMessage }}</p>
    </div>
  </template>
    
  <script setup>
  import { useRouter } from 'vue-router'
  import { ref } from 'vue'
  
  const router = useRouter()
  const adminId = ref('')
  const adminPassword = ref('')
  const errorMessage = ref('')
  
  function login() {
    // 간단한 관리자 인증 시뮬레이션
    if (adminId.value === 'admin' && adminPassword.value === 'admin123') {
      localStorage.setItem('adminAuth', 'true') // 관리자 인증 상태 저장
      alert('관리자 로그인 성공!')
      router.push({ name: 'ManageBoards' }) // 관리자 메인으로 이동
    } else {
      errorMessage.value = '아이디 또는 비밀번호가 잘못되었습니다.'
    }
  }
  </script>
  