<template>
  <div class="signup-container">
    <div class="signup-header">
      <h2>개인정보 수정</h2>
    </div>
    <form @submit.prevent="signup" class="signup-form">
      <div class="form-group-row">
        <label for="username">이름</label>
        <input id="username" v-model="username" type="text" placeholder="이름" required />
      </div>
      <div class="form-group-row">
        <label for="userLoginId">아이디</label>
        <input id="userLoginId" v-model="userLoginId" type="text" placeholder="아이디" readonly />
      </div>
      <div class="form-group-row">
        <label for="password">비밀번호</label>
        <input id="password" v-model="password" type="password" placeholder="비밀번호" required />
      </div>
      <div class="form-group-row">
        <label for="confirmPassword">비밀번호 확인</label>
        <div class="input-container">
          <input
            id="confirmPassword"
            v-model="confirmPassword"
            type="password"
            placeholder="비밀번호 확인"
            required
          />
          <p v-if="!passwordsMatch" class="error-message">비밀번호가 일치하지 않습니다.</p>
        </div>
      </div>
      <div class="form-group-row">
        <label for="email">이메일</label>
        <div class="input-container email-container">
          <input id="email" v-model="email" type="email" placeholder="이메일" @input="validateEmail" />
        </div>
      </div>
      <div class="form-group-row">
        <label for="birthDate">생년월일</label>
        <input id="birthDate" v-model="birthDate" type="text" placeholder="생년월일" readonly />
      </div>
      <div class="form-group-row">
        <label for="gender">성별</label>
        <select id="gender" v-model="gender" disabled>
          <option value="M">남성</option>
          <option value="F">여성</option>
        </select>
      </div>
      <div class="lonely-button">
        <button type="button" class="btn-tertiary" @click="confirmLeave">회원 탈퇴하기</button>
      </div>
      <div class="button-group">
        <button type="submit" class="btn-primary" :disabled="!passwordsMatch">확인</button>
        <button type="button" class="btn-secondary" @click="confirmCancel">취소</button>
      </div>
    </form>
    <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import router from "@/router";
import { useUserStore } from "@/store/useUserStore";

const username = ref("");
const userLoginId = ref("");
const password = ref("");
const confirmPassword = ref("");
const email = ref("");
const birthDate = ref(""); // 생년월일 수정 가능하도록
const gender = ref("");
const errorMessage = ref("");
const successMessage = ref("");
const store = useUserStore();
const isAuthenticated = ref(false)

// 비밀번호가 일치하는지 확인하는 computed 속성
const passwordsMatch = computed(() => password.value === confirmPassword.value);

function signup() {
  if (!passwordsMatch.value) {
    errorMessage.value = "비밀번호가 일치하지 않습니다.";
    successMessage.value = "";
    return;
  }

  try {
    // 비동기 요청을 기다림
    store.updateUser(username.value, password.value, email.value);
    // 성공 메시지 처리
    errorMessage.value = "";
    successMessage.value = "수정이 완료되었습니다!";
  } catch (error) {
    errorMessage.value = "수정 중 문제가 발생했습니다.";
  }
}

function confirmCancel() {
  const confirmCancel = confirm("취소하시겠습니까?");
  if (confirmCancel) {
    
  }
}

function confirmLeave() {
  const confirmLeave = confirm("탈퇴하시겠습니까?");
  if (confirmLeave) {
    store.deleteUser();
  }
}

onMounted(() => {
  try {
    store.checkAuth().then((loggedInUser) => {
      if (loggedInUser == null) {
        isAuthenticated.value = false;
      } else {
        console.log("로그인 유저: ", loggedInUser);
        username.value = loggedInUser.username;
        userLoginId.value = loggedInUser.userLoginId;
        password.value = loggedInUser.password;
        confirmPassword.value = loggedInUser.password; // 비밀번호와 비밀번호 확인 초기화
        email.value = loggedInUser.email;
        birthDate.value = loggedInUser.birthDate.substring(0, 10);; // 초기화 가능
        gender.value = loggedInUser.gender;
        isAuthenticated.value = true;
      }
    });
  } catch (error) {
    console.error("Error during authentication:", error);
  }
});
</script>


<style scoped>
.signup-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin: auto;
}

.signup-header {
  background-color: #043873;
  color: white;
  width: 100%;
  text-align: center;
  padding: 80px 0;
}

.signup-header h2 {
  margin-bottom: 0px;
}

.signup-form {
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 800px;
  gap: 15px;
  padding: 60px;
  margin-top: 30px;
}

.form-group-row {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 20px;
}

.form-group-row label {
  width: 200px !important;
  padding: 10px;
  font-weight: bold;
  color: #333;
}

.form-group-row input,
.form-group-row select {
  width: 100%;
  max-width: 580px;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

/* readonly 상태의 input 및 disabled 상태의 select */
.signup-form input[readonly],
.signup-form select[disabled] {
  background-color: #f9f9f9; /* 배경색으로 읽기 전용 강조 */
  border: none; /* 테두리 제거 */
  color: #333; /* 텍스트 색상 */
  cursor: not-allowed; /* 마우스 포인터 */
  outline: none; /* 포커스 시 외곽선 제거 */
  padding-left: 0; /* 여백 조정 */
}

/* 비밀번호 필드와 일반 필드 */
.form-group-row input:not([readonly]),
.form-group-row select:not([disabled]) {
  border: 1px solid #ccc; /* 일반 필드에 테두리 유지 */
}

.input-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: calc(100% - 220px);
}

/* 이메일 input-container를 상대 위치로 설정 */
.email-container,
.verification-container {
  position: relative;
  width: 100%;
}

.email-container input {
  width: calc(100% - 45px); /* 인증 버튼 크기를 고려한 너비 */
  margin-left: 45px;
  box-sizing: border-box; /* 패딩과 border 포함 */
}

.error-message {
  color: red;
  font-size: 12px;
  margin-top: 5px;
}

.success-message {
  color: green;
  font-size: 12px;
  text-align: center;
  margin-top: 10px;
}

.button-group {
  display: flex;
  justify-content: center;
}

.btn-primary {
  background-color: #043873;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  margin: 5px;
  cursor: pointer;
}

.btn-primary:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-secondary {
  background-color: #ccc;
  color: #333;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  margin: 5px;
  cursor: pointer;
}

.btn-secondary:hover {
  background-color: #aaa;
}

.btn-tertiary {
  background-color: #ffffff;
  color: #333;
  border: none;
  text-decoration: underline;
}

.btn-tertiary:hover {
  color: red;
}

.lonely-button {
  display: flex;
  justify-content: right;
}
</style>