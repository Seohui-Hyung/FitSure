<template>
    <div class="blurred-box">
      <div class="user-login-box">
        <span class="user-icon"></span>
        <div class="user-name">관리자 로그인 사이트 입니다.</div>
        <input
          class="user-password"
          type="text"
          placeholder="아이디"
          id="adminName"
          v-model.trim="adminName"
        />
        <p></p>
        <input
          class="user-password"
          type="password"
          placeholder="비밀번호"
          id="adminPassword"
          v-model.trim="adminPassword"
        />
        <p></p>
        <input
          type="submit"
          name="Login"
          value="Login"
          class="login-submit"
          @click="login"
        />
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useAdminStore } from '@/store/useAdminStore';
  
  const store = useAdminStore();
  const adminName = ref('');
  const adminPassword = ref('');
  
  function login() {
    if (!adminName.value || !adminPassword.value) {
      alert('아이디와 비밀번호를 입력해주세요.');
      return;
    }
    store.adminLogin(adminName.value, adminPassword.value);
  }
  </script>
  
  <style>
 @font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;
  background-position: top;
  background-image: url('@/assets/images/admin_background.jpg');
  font-family: 'GmarketSansMedium', Arial, Helvetica, sans-serif;
  font-weight: normal;
  font-style: normal;
  letter-spacing: 0.02em;
  -webkit-font-smoothing: antialiased;
}

.blurred-box {
  width: 500px;
  height: 600px;
  background: inherit;
  border-radius: 15px;
  overflow: hidden;
  position: relative;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.blurred-box:after {
  content: '';
  width: 550px;
  height: 550px;
  background: inherit;
  position: absolute;
  left: -25px;
  top: -25px;
  box-shadow: inset 0 0 0 200px rgba(255, 255, 255, 0.1);
  filter: blur(15px);
}

.user-login-box {
  position: relative;
  margin-top: 70px;
  text-align: center;
  z-index: 1;
}

.user-login-box > * {
  display: inline-block;
  width: 350px; /* 필드 가로 크기 */
}

.user-icon {
  width: 150px; /* 아이콘 크기 */
  height: 150px;
  margin: 0 auto;
  display: block;
  border-radius: 50%;
  background-size: cover;
  background-image: url('@/assets/images/ketchap_health.jpg');
}

.user-name {
  margin-top: 20px;
  margin-bottom: 30px;
  color: black;
  font-size: 24px;
}

input.user-password {
  width: 300px;
  height: 40px;
  opacity: 0.9;
  border-radius: 8px;
  padding: 10px;
  border: 1px solid #ccc;
  font-size: 16px;
}

input.login-submit {
  margin-top: 20px;
  width: 200px;
  height: 50px;
  background-color: #043873;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 18px;
}

input.login-submit:hover {
  background-color: #0056b3;
}

  </style>
  