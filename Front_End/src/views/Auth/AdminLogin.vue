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
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
      format('woff');
  }
  
  * {
    margin: 0;
    padding: 0;
  }
  
  html {
    width: 100vw;
    height: 100vh;
  }
  
  body {
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    background-position: top;
    background-image: url('@/assets/images/admin_background.png');
    width: 100%;
    height: 100%;
    letter-spacing: 0.02em;
    font-family: 'GmarketSansMedium', Arial, Helvetica, sans-serif;
    font-weight: normal;
    font-style: normal;
    -webkit-font-smoothing: antialiased;
  }
  
  .blurred-box {
    position: relative;
    width: 500px; /* 가로 크기 증가 */
    height: 600px; /* 높이 증가 */
    top: calc(50% - 250px); /* 중앙 정렬 */
    left: calc(50% - 250px);
    background: inherit;
    border-radius: 15px;
    overflow: hidden;
  }
  
  .blurred-box:after {
    content: '';
    width: 550px; /* 블러 크기 증가 */
    height: 550px;
    background: inherit;
    position: absolute;
    left: -25px;
    top: -25px;
    box-shadow: inset 0 0 0 200px rgba(255, 255, 255, 0.1);
    filter: blur(15px); /* 블러 강도 증가 */
  }
  
  .user-login-box {
    position: relative;
    margin-top: 70px; /* 위 여백 조정 */
    text-align: center;
    z-index: 1;
  }
  
  .user-login-box > * {
    display: inline-block;
    width: 350px; /* 필드 크기 증가 */
  }
  
  .user-icon {
    width: 150px; /* 크기 증가 */
    height: 150px;
    margin: 0 auto; /* 중앙 정렬 */
    display: block;
    border-radius: 50%;
    background-size: cover;
    background-image: url('@/assets/images/ketchap_health.jpg');
  }
  
  .user-name {
    margin-top: 20px;
    margin-bottom: 30px;
    color: black;
    font-size: 24px; /* 폰트 크기 증가 */
  }
  
  input.user-password {
    width: 300px; /* 필드 가로 크기 증가 */
    height: 40px; /* 필드 높이 증가 */
    opacity: 0.9;
    border-radius: 8px;
    padding: 10px;
    border: 1px solid #ccc;
    font-size: 16px; /* 폰트 크기 증가 */
  }
  
  input.login-submit {
    margin-top: 20px;
    width: 200px; /* 버튼 크기 증가 */
    height: 50px; /* 버튼 높이 증가 */
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 18px; /* 버튼 폰트 크기 증가 */
  }
  
  input.login-submit:hover {
    background-color: #0056b3; /* 호버 색상 */
  }
  </style>
  