<template>
    <div>
      <!-- 상단 네비게이션 -->
      <nav>
        <button v-for="step in steps" :key="step.id" @click="goToStep(step.id)">
          {{ step.id }}단계
        </button>
      </nav>
  
      <!-- 동적 컴포넌트 렌더링 -->
      <component :is="steps.find((step) => step.id === currentStep).component" />
  
      <!-- 이전 및 다음 버튼 -->
      <div>
        <button @click="goToStep(currentStep - 1)" :disabled="currentStep === 1">이전</button>
        <button @click="goToStep(currentStep + 1)" :disabled="currentStep === steps.length">
          다음
        </button>
      </div>
    </div>
</template>

<script setup>
import { ref } from "vue";

// 단계별 컴포넌트 import
import Step1 from "./components/payment/Step1.vue"; // 고객 정보, 선택 보험 정보 확인 (피보험자와 계약자가 동일해야 한다)
import Step2 from "./components/payment/Step2.vue"; // 알릴 사항
import Step3 from "./components/payment/Step3.vue"; // 쿠폰 적용 및 결제 금액 안내 후 결제하기 


// 현재 단계를 관리하는 변수
const currentStep = ref(1);

// 단계 변경 함수
const goToStep = (step) => {
  currentStep.value = step;
};

// 단계별 컴포넌트 매핑
const steps = [
  { id: 1, component: Step1 },
  { id: 2, component: Step2 },
  { id: 3, component: Step3 },
];


</script>


