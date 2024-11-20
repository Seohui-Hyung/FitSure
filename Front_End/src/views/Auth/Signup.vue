<template>
    <div>
      <h2>회원가입</h2>
      <form @submit.prevent="signup">
        <label for="username">아이디</label>
        <input id="username" v-model="username" type="text" required />
  
        <label for="password">비밀번호</label>
        <input id="password" v-model="password" type="password" required />
  
        <label for="confirmPassword">비밀번호 확인</label>
        <input id="confirmPassword" v-model="confirmPassword" type="password" required />
  
        <button type="submit">회원가입</button>
      </form>

      <p v-if="errorMessage" style="color: red">{{ errorMessage }}</p>
      <p v-if="successMessage" style="color: green">{{ successMessage }}</p>
    </div>
  </template>
  
  <script setup>
    import { ref } from 'vue';
    import { useRouter } from 'vue-router';
    import { errorMessages } from 'vue/compiler-sfc';

    const username = ref('')
    const password = ref('')
    const confirmPassword = ref('')
    const errorMessage = ref('')
    const successMessage = ref('')
    const router = useRouter()
    
    function signup() {
      if (password.value !== confirmPassword.value) {
        errorMessage.value = '비밀번호가 일치하지 않습니다.'
        successMessage.value = ''
        return
      }
    
      const users = JSON.parse(localStorage.getItem('users') || '[]')
      if (users.some(user => user.username === username.value)) {
        errorMessage.value = '이미 사용 중인 아이디입니다.'
        successMessage.value = ''
      } else {
        users.push({ username: username.value, password: password.value })
        localStorage.setItem('users', JSON.stringify(users))
        successMessage.value = '회원가입이 완료되었습니다!'
        errorMessage.value = ''

        setTimeout(() => {
          router.push({ name: 'login' }) // 로그인 화면으로 이동
        }, 2000)
      }
    }
  </script>
  