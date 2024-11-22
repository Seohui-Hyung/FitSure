<template>
    <header class="header">
        <div class="logo-section">
            <img class="logo" src="@/assets/images/Fitsure_main.png" alt="로고 A" />
        </div>
        <div class="actions-section">
            <div class="logo">{{ title }}</div>
        </div>
    </header>
</template>

<script setup>
import { ref } from 'vue';

const title = ref('FIT SURE'); // 페이지 제목
</script>

<style scoped>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #043873;
    color: white;
    padding: 10px 20px;
    height: 80px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.logo-section,
.actions-section {
    flex: 0 0 120px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.logo {
    width: 200px;
    height: 200px; 
    display: inline-block; /* 이미지를 inline 요소로 유지 */
    object-fit: contain; /* 이미지 비율 유지 */
}

.header-title {
    flex: 1;
    font-size: 20px;
    font-weight: bold;
    text-align: center;
}
</style>
