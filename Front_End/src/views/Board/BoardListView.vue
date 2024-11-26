<template>
    <div class="health-insurance-container">
      <!-- 페이지 내용 -->
      <div class="content">
        <h2 class="title">이용 안내</h2>
        <nav class="tab-nav">공지사항</nav>
        <div class="content-box">
            <RouterView />
        </div>
      </div>
    </div>
</template>

<script setup>
  import { ref } from "vue";
  
  // 탭 정보 정의
  const tabs = ref([
    { label: "공지사항"},
  ]);
  
  const activeTab = ref(0); // 기본 탭 설정
  
  function selectTab(index) {
    activeTab.value = index;
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

  .tab-nav {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    background-color: #043873;
    color: white;
    height: 40px;
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
</style>