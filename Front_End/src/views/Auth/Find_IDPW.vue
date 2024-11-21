<template>
    <div class="find-container">
      <div class="tabs">
        <!-- 탭 버튼 -->
        <button 
          :class="{ active: activeTab === 'findId' }" 
          @click="activeTab = 'findId'"
        >
          아이디 찾기
        </button>
        <button 
          :class="{ active: activeTab === 'findPassword' }" 
          @click="activeTab = 'findPassword'"
        >
          비밀번호 찾기
        </button>
      </div>
  
      <!-- 아이디 찾기 -->
      <div v-if="activeTab === 'findId'" class="tab-content">
        <h2>아이디 찾기</h2>
        <form @submit.prevent="findId">
          <label for="name">이름</label>
          <input id="name" v-model="name" type="text" placeholder="이름 입력" required />
  
          <label for="dob">생년월일</label>
          <input id="dob" v-model="dob" type="date" placeholder="생년월일 입력" required />
  
          <label for="email">이메일</label>
          <input id="email" v-model="email" type="email" placeholder="이메일 입력" required />
  
          <button type="submit">아이디 찾기</button>
        </form>
      </div>
  
      <!-- 비밀번호 찾기 -->
      <div v-if="activeTab === 'findPassword'" class="tab-content">
        <h2>비밀번호 찾기</h2>
        <form @submit.prevent="findPassword">
          <label for="username">아이디</label>
          <input id="username" v-model="username" type="text" placeholder="아이디 입력" required />
  
          <label for="dob">생년월일</label>
          <input id="dob-password" v-model="dob" type="date" placeholder="생년월일 입력" required />
  
          <label for="email-password">이메일</label>
          <input id="email-password" v-model="email" type="email" placeholder="이메일 입력" required />
  
          <button type="submit">비밀번호 찾기</button>
        </form>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  
  const activeTab = ref("findId"); // 현재 활성화된 탭 ("findId" 또는 "findPassword")
  const name = ref("");
  const dob = ref("");
  const email = ref("");
  const username = ref("");
  
  function findId() {
    alert(`아이디 찾기 진행: 이름(${name.value}), 생년월일(${dob.value}), 이메일(${email.value})`);
  }
  
  function findPassword() {
    alert(`비밀번호 찾기 진행: 아이디(${username.value}), 생년월일(${dob.value}), 이메일(${email.value})`);
  }
  </script>
  
  <style scoped>
  .find-container {
    text-align: center;
  }
  
  .tabs {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
  }
  
  button {
    padding: 10px 20px;
    margin: 0 10px;
    background-color: #ccc;
    border: none;
    cursor: pointer;
    font-size: 16px;
  }
  
  button.active {
    background-color: #043873;
    color: white;
  }
  
  .tab-content {
    margin-top: 20px;
  }
  
  form {
    display: flex;
    flex-direction: column;
    gap: 15px;
    align-items: center;
  }
  
  input {
    padding: 10px;
    font-size: 14px;
    width: 300px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  button[type="submit"] {
    padding: 10px 20px;
    background-color: #043873;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  button[type="submit"]:hover {
    background-color: #0056b3;
  }
  </style>  