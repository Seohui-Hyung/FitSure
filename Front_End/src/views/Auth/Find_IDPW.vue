<template>
    <div class="find-container">
      <div class="header">
        <h2>아이디 / 비밀번호 찾기</h2>
      </div>
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
        <form @submit.prevent="findId">
          <input id="username" v-model="username" type="text" placeholder="이름을 입력해주세요" required />
          <input id="email" v-model="email" type="email" placeholder="이메일을 입력해주세요" required />
  
          <div class="buttons">
            <button type="submit">아이디 찾기</button>
            <button type="button" @click="cancel">취소</button>
          </div>
        </form>
        <!-- 결과 출력 -->
        <div v-if="searchAttempted">
          <p v-if="foundId">아이디: {{ foundId }}</p>
          <p v-else>일치하는 아이디가 없습니다.</p>
        </div>
      </div>
  
      <!-- 비밀번호 찾기 -->
      <div v-if="activeTab === 'findPassword'" class="tab-content">
        <form @submit.prevent="findPassword">
          <input id="userLoginId" v-model="userLoginId" type="text" placeholder="아이디를 입력해주세요" required />
          <input id="email-password" v-model="email" type="email" placeholder="이메일을 입력해주세요" required />
  
          <div class="buttons">
            <button type="submit">비밀번호 찾기</button>
            <button type="button" @click="cancel">취소</button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import { useUserStore } from "@/store/useUserStore";

  const activeTab = ref("findId"); // 현재 활성화된 탭 ("findId" 또는 "findPassword")
  const userStore = useUserStore();

  const {
    username,
    email,
    userLoginId,
    foundId,
    foundPassword,
    searchAttempted,
    findId,
    findPassword,
    resetForm,
  } = userStore;
  
  // function findId() {
  //   alert(`아이디 찾기 진행: 이름(${name.value}), 생년월일(${dob.value}), 이메일(${email.value})`);
  // }
  
  // function findPassword() {
  //   alert(`비밀번호 찾기 진행: 아이디(${username.value}), 생년월일(${dob.value}), 이메일(${email.value})`);
  // }

  function cancel() {
    if (confirm("취소하시겠습니까?")) {
      window.location.href = "/login";
    }
  }
  </script>
  
  <style scoped>
  .find-container {
    text-align: center;
  }
  
  /* 상단 제목 스타일 */
  .header {
    background-color: #043873;
    color: white;
    padding: 80px 0;
  }

  .header h2 {
    margin-bottom: 0px;
  }

  .tabs {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
  }
  
  button {
    flex: 1;
    padding: 10px 20px;
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
    margin-top: 100px;
    display: flex;
    flex-direction: column;
    gap: 15px;
    align-items: center;
  }
  
  input {
    padding: 10px;
    font-size: 14px;
    width: 310px;
    border: 1px solid #ccc;
    border-radius: 5px;
    text-align: center;
  }

  input::placeholder {
  text-align: center; /* placeholder 가운데 정렬 */
  color: #999;
}

.buttons {
  display: flex;
  gap: 20px;
  margin-top: 25px;
}

button[type="submit"] {
  padding: 10px 20px;
  background-color: #043873;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 220px;
}

button[type="button"] {
  padding: 10px 20px;
  background-color: #999;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button[type="submit"]:hover {
  background-color: #3670ad;
}

button[type="button"]:hover {
  background-color: #ccc;
}
</style>  