<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <img src="@/assets/images/Fitsure_login_Blue.png" alt="Fitsure Logo" class="logo" />
      </div>
      <form @submit.prevent="login" class="login-form">
        <div class="input-group">
          <div class="input-container">
            <input id="username" v-model="username" type="text" placeholder="아이디" class="input-box" required />
            <input id="password" v-model="password" type="password" placeholder="비밀번호" class="input-box" required />
          </div>
          <button type="submit" class="login-button">Login</button>
        </div>
      </form>
      <div class="login-footer">
        <RouterLink to="/find-id" class="link">아이디 / 비밀번호 찾기</RouterLink>
        <pre>     |     </pre>
        <RouterLink to="/signup" class="link">회원가입</RouterLink>
      </div>
    </div>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
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

<style scoped>
/* 전체 컨테이너 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75vh; /* 화면 전체 높이 */
  padding: 20px;
  box-sizing: border-box;
}

/* 로그인 박스 */
.login-box {
  width: 800px;
  padding: 30px;
  background-color: #e8eaef; /* 흰 배경 */
  /* border-radius: 10px; */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 */
  text-align: center;
}

/* 로고 */
.logo {
  width: 250px; /* 로고 크기 조정 */
  margin-bottom: 20px;
}

/* 폼 스타일 */
.login-form {
  display: flex;
  flex-direction: column;
  gap: 20px; /* 입력창 간격 */
  margin-top: 20px;
}

/* 입력 그룹 */
.input-group {
  display: flex;
  align-items: stretch; /* 라벨과 입력창 수평 정렬 */
  justify-content: center;
  position: relative;
}

/* 입력 컨테이너 */
.input-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: auto;
  max-width: 270px;
  gap: 10px;
  flex: 1; /* 버튼과 균형 맞추기 */
  overflow: visible;
}

/* 입력 박스 */
.input-box {
  width: 90%;
  padding: 15px; /* 높이 조정 */
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
  text-align: center; /* placeholder 중앙 정렬 */
  margin: 0 auto;
}

/* 로그인 버튼 */
.login-button {
  width: 100px;
  background-color: #043873; /* 파란색 */
  color: white;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: background-color 0.3s;
}

.login-button:hover {
  background-color: #0056b3; /* 호버 색상 */
}

/* 하단 링크 */
.login-footer {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.link {
  font-size: 14px;
  color: #043873;
  text-decoration: none;
}

.link:hover {
  text-decoration: underline;
}

/* 에러 메시지 */
.error-message {
  color: red;
  font-size: 14px;
  margin-top: 10px;
}
</style>