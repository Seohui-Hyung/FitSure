<template>
  <div class="step2-container">
    <!-- 상단 사용자 이름 -->
    <h2 class="header">
      <span class="highlight">{{ username }}</span> 님의 보험 가입에 필요한 정보를 알려주세요
    </h2>
    <p class="sub-header">보험금 지급에 문제가 없도록 꼭 사실대로 알려주세요.</p>

    <!-- 질문 목록 -->
    <div v-for="question in questions" :key="question.id" class="question-block">
      <h3 class="question-title">{{ question.id }}. {{ question.text }}</h3>
      <div class="options">
        <label class="radio-option">
          <input
            type="radio"
            :name="'question-' + question.id"
            :value="true"
            @click="selectAnswer(question.id, true)"
          />
          예
        </label>
        <label class="radio-option">
          <input
            type="radio"
            :name="'question-' + question.id"
            :value="false"
            @click="selectAnswer(question.id, false)"
          />
          아니오
        </label>
      </div>
    </div>

    <!-- 경고 메시지 -->
    <p v-if="alertMessage" class="alert-message">{{ alertMessage }}</p>

    <!-- 다음 버튼 -->
    <button class="next-button" :disabled="!isCompleted()" @click="$emit('nextStep')">다음</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

// 질문 목록과 상태 관리
const questions = ref([
  { id: 1, text: "자동차(건설기계, 농기계 포함) 및 의료자동차를 운전하시나요?", answer: null },
  { id: 2, text: "전동킥보드, 전동이륜평행차 등 원동기장치 자전거를 사용하시나요?", answer: null },
  { id: 3, text: "현재 지병을 가지고 계신가요?", answer: null },
  { id: 4, text: "최근 1년 이내 질병으로 치료를 받은 적이 있습니까?", answer: null },
  { id: 5, text: "최근 5년 이내 질병으로 진단받거나 치료받은 적이 있습니까?", answer: null },
]);

// 경고 메시지 관리
const alertMessage = ref("");

// 선택 처리
const selectAnswer = (id, answer) => {
  const unanswered = questions.value.find((q) => q.id < id && q.answer === null);
  if (unanswered) {
    alertMessage.value = `질문 ${unanswered.id}에 답변을 먼저 선택해주세요.`;
    setTimeout(() => (alertMessage.value = ""), 3000);
    return;
  }

  const question = questions.value.find((q) => q.id === id);
  if (question) {
    question.answer = answer;
  }
};

// 모든 질문이 완료되었는지 확인
const isCompleted = () => {
  return questions.value.every((q) => q.answer !== null);
};

// 상태 관리 변수
const userName = ref("");

// JWT 디코드 함수
const decodeToken = (token) => {
  if (!token) return null;

  try {
    const base64Url = token.split('.')[1]; // JWT의 Payload 부분
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map((c) => `%${`00${c.charCodeAt(0).toString(16)}`.slice(-2)}`)
        .join('')
    );
    return JSON.parse(jsonPayload); // 디코딩된 Payload 반환
  } catch (error) {
    console.error("Failed to decode token:", error);
    return null;
  }
};


onMounted(() => {
  const token = localStorage.getItem("access-token"); 

  if (!token) {
    console.error("Access token not found");
    return;
  }

  const payload = decodeToken(token); 

  if (payload && payload.userName) {
    userName.value = payload.userName; 
    console.log(`Welcome, ${userName.value}`);
  } else {
    console.error("userName not found in token");
  }
});
</script>

<style scoped>
/* 전체 컨테이너 스타일 */
.step2-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

/* 헤더 스타일 */
.header {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.sub-header {
  font-size: 14px;
  color: #555;
  margin-bottom: 20px;
}

/* 강조된 사용자 이름 스타일 */
.highlight {
  color: #007bff; /* 파란색 */
}

/* 질문 블록 스타일 */
.question-block {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.question-title {
  font-size: 16px;
  margin-bottom: 10px;
  color: #333;
}

/* 라디오 버튼 옵션 스타일 */
.options {
  display: flex;
  gap: 15px;
}

.radio-option {
  font-size: 14px;
  color: #333;
}

/* 경고 메시지 스타일 */
.alert-message {
  font-size: 14px;
  color: red;
  margin-top: 20px;
}

/* 다음 버튼 스타일 */
.next-button {
  padding: 10px 20px;
  font-size: 16px;
  color: white;
  background-color: #007bff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.next-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
