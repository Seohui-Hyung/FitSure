<template>
    <div class="health-insurance-container">
      <!-- Header 컴포넌트 -->
      <Header />
  
      <!-- 페이지 내용 -->
      <div class="content">
        <!-- 건강 보험 제목 -->
        <h2 class="title">건강 보험</h2>
  
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
  
        <!-- 보험 가입 버튼 -->
        <button class="subscribe-button">보험 가입하기</button>
      </div>
  
      <!-- Footer 컴포넌트 -->
      <Footer />
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import Header from "@/components/common/Header.vue";
  import Footer from "@/components/common/Footer.vue";
  
  // 탭 정보 정의
  const tabs = ref([
    { label: "건강 보험(6~40)", image: "/assets/health1.jpg" }, // 실제 이미지 경로로 대체
    { label: "건강 보험(41~70)", image: "/assets/health2.jpg" },
    { label: "일 보험", image: "/assets/daily.jpg" },
  ]);
  
  const activeTab = ref(0); // 기본 탭 설정
  
  function selectTab(index) {
    activeTab.value = index;
  }
  </script>
  
  <style scoped>
  /* 전체 컨테이너 스타일 */
  .health-insurance-container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100vh;
    background-color: #f5f5f5;
  }
  
  /* 제목 스타일 */
  .title {
    text-align: center;
    font-size: 24px;
    margin: 20px 0;
    color: #043873;
  }
  
  /* 네비게이션 탭 스타일 */
  .tab-nav {
    display: flex;
    justify-content: center;
    background-color: #043873;
    padding: 10px 0;
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
    background-color: #0056b3;
    color: white;
  }
  
  /* 이미지 표시 영역 */
  .image-container {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80%;
    background-color: #e8eaf0;
    border: 1px solid #ccc;
    border-radius: 10px;
    margin: 20px auto;
    padding: 20px;
  }
  
  .insurance-image {
    max-width: 100%;
    max-height: 100%;
    border-radius: 10px;
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
    background-color: #ffd700;
    color: black;
    font-size: 18px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .subscribe-button:hover {
    background-color: #ffc107;
  }
  </style>  