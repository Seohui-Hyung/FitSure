<template>
    <div>
        <!-- 상단 단계 진행 바 -->
        <div class="progress-bar">
            <div v-for="step in steps" :key="step.id" class="progress-step">
                <div class="progress-circle" :class="{ active: step.id <= currentStep }">
                    <span>{{ step.id }}</span>
                </div>
                <div v-if="step.id < steps.length" class="progress-line" :class="{ active: step.id < currentStep }">
                </div>
            </div>
        </div>

        <!-- 현재 단계 컴포넌트 렌더링 -->
        <component :is="steps.find((step) => step.id === currentStep).component" />

        <!-- 하단 내비게이션 버튼 -->
        <div class="navigation-buttons">
            <button @click="goToStep(currentStep - 1)" :disabled="currentStep === 1" class="nav-button">
                이전
            </button>
            <button @click="goToStep(currentStep + 1)" :disabled="currentStep === steps.length" class="nav-button">
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

const currentStep = ref(1);

const goToStep = (step) => {
    if (step >= 1 && step <= steps.length) {
        currentStep.value = step;
    }
};

const steps = [
    { id: 1, component: Step1 },
    { id: 2, component: Step2 },
    { id: 3, component: Step3 },
];
</script>

<style scoped>
/* 진행 바 스타일 */
.progress-bar {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    flex-wrap: nowrap;
    gap: 20px;
    width: 100%;
    max-width: 1200px;
    padding-top: 20px;
    padding-bottom: 40px;
}

.progress-step {
    display: flex;
    align-items: center;
    gap: 20px;
}

.progress-circle {
    width: 50px;
    height: 50px;
    border: 2px solid #ddd;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 16px;
    background-color: white;
    color: #ddd;
    font-weight: bold;
    transition: background-color 0.3s, color 0.3s, border-color 0.3s;
}

.progress-circle.active {
    border-color: #007bff;
    background-color: #007bff;
    color: white;
}

.progress-line {
    width: 100px;
    height: 2px;
    background-color: #ddd;
    transition: background-color 0.3s;
}

.progress-line.active {
    background-color: #007bff;
}

.navigation-buttons {
    display: flex;
    justify-content: space-between;
    gap: 120px;
    margin-top: 20px;
    margin-left: 30px;
    margin-right: 30px;
}

.nav-button {
    flex: none;
    text-align: center;
    padding: 10px 26px;
    font-size: 17px;
    border: none;
    border-radius: 5px;
    background-color: #007bff;
    color: white;
    cursor: pointer;
}

.nav-button:hover {
    background-color: #0056b3;
}

.nav-button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}
</style>