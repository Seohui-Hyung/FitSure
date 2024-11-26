<template>
    <div class="health-insurance-container">
      <!-- 페이지 내용 -->
      <div class="content">
        <!-- 건강 보험 제목 -->
        <h2 class="title">여행자 보험</h2>
  
        <!-- 네비게이션 탭 -->
        <nav class="tab-nav">
          <button
            v-for="(tab, index) in tabs"
            :key="index"
            :class="{ active: activeTab === index }"
            @click="selectTab(index)"
            class="tab-button"
          >
            {{ tab.label }}
          </button>
        </nav>
  
        <!-- 이미지 표시 -->
        <div class="image-container">
          <img :src="tabs[activeTab].image" alt="보험 이미지" class="insurance-image" />
          <p class="image-placeholder" v-if="!tabs[activeTab].image">대응 이미지</p>
        </div>
      </div>
  
      <!-- 보험 가입 버튼 -->
      <button class="subscribe-button" @click="goToPayRoutine">보험 가입하기</button>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import { useRouter } from "vue-router";
  
  // Vue Router 사용
  const router = useRouter();
  
  // 탭 정보 정의
  const tabs = ref([
    { label: "해외 여행 보험", image: "/images/insurance/여행자 보험.png" },
  ]);
  
  const activeTab = ref(0); // 기본 탭 설정
  
  function selectTab(index) {
    activeTab.value = index;
  }
  
  // 보험 가입 버튼 동작
  function goToPayRoutine() {
    const insuranceId = 1; 
    router.push(`/insurance/pay/${insuranceId}`);
  }
  </script>
  
  <style scoped>
  /* 제목 스타일 */
  .title {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    font-size: 24px;
    background-color: #043873;
    color: white;
    height: 150px;
    margin: 0;
  }
  
  /* 네비게이션 탭 스타일 */
  .tab-nav {
    display: flex;
    justify-content: center;
    background-color: #043873;
  }
  
  .tab-button {
    flex: 1;
    padding: 10px 20px;
    background-color: #e8eaf0;
    color: #043873;
    border: none;
    font-size: 16px;
    cursor: pointer;
  }
  
  .tab-button.active {
    background-color: #043873;
    color: white;
  }
  
  .tab-button:hover {
    background-color: #3670ad;
    color: white;
  }
  
  /* 이미지 표시 영역 */
  .image-container {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80%;
    margin: 20px auto;
    padding: 20px;
  }
  
  .insurance-image {
    max-width: 100%;
    max-height: 100%;
  }
  
  .image-placeholder {
    color: #777;
    font-size: 18px;
    text-align: center;
  }
  
  /* 보험 가입 버튼 스타일 */
  .subscribe-button {
    display: block;
    margin: 20px auto;
    padding: 10px 20px;
    background-color: #FFE492;
    color: #043873;
    font-size: 18px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .subscribe-button:hover {
    background-color: #ffda70;
  }
  </style>
  