<template>
  <div>
    <!-- 상단 네비게이션 바 -->
    <div class="progress-bar">
      <div
        v-for="step in steps"
        :key="step.id"
        class="progress-circle"
        :class="{ active: step.id <= currentStep }"
      >
        <span>{{ step.id }}</span>
      </div>
    </div>

    <!-- 동적 컴포넌트 렌더링 -->
    <component :is="steps.find((step) => step.id === currentStep).component" />

    <!-- 이전 및 다음 버튼 -->
    <div class="navigation-buttons">
      <button @click="goToStep(currentStep - 1)" :disabled="currentStep === 1">
        이전
      </button>
      <button @click="goToStep(currentStep + 1)" :disabled="currentStep === steps.length">
        다음
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

// 단계별 컴포넌트 import
import Step1 from "@/components/payment/Step1.vue";
import Step2 from "@/components/payment/Step2.vue";
import Step3 from "@/components/payment/Step3.vue";

// 현재 단계를 관리하는 변수
const currentStep = ref(1);

// 단계 변경 함수
const goToStep = (step) => {
  if (step >= 1 && step <= steps.length) {
    currentStep.value = step;
  }
};

// 단계별 컴포넌트 매핑
const steps = [
  { id: 1, component: Step1 },
  { id: 2, component: Step2 },
  { id: 3, component: Step3 },
];
</script>

<style scoped>
/* 상단 네비게이션 스타일 */
.progress-bar {
  display: flex; /* 가로로 정렬 */
  justify-content: center; /* 중앙 정렬 */
  align-items: center; /* 수직 정렬 */
  margin-bottom: 20px;
  gap: 20px; /* 각 동그라미 간 간격 */
}

.progress-circle {
  width: 40px;
  height: 40px;
  border: 2px solid #ddd;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  background-color: white;
  color: #ddd;
  transition: background-color 0.3s, color 0.3s, border-color 0.3s;
}

.progress-circle.active {
  border-color: #007bff;
  background-color: #007bff;
  color: white;
}

/* 버튼 스타일 */
.navigation-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  transition: background-color 0.3s;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
