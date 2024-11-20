<template>
  <div class="main-container">
    <!-- 홍보 자료 슬라이더와 로그인 폼 -->
    <section class="promo-login-section">
      <!-- 홍보 슬라이더 -->
      <div class="promo-slider" @mouseenter="pauseSlider" @mouseleave="resumeSlider">
        <img :src="slides[currentSlide]" alt="Promo Image" class="promo-image" />
        <div class="pagination">
          <span 
            v-for="(slide, index) in slides" 
            :key="index" 
            :class="{ active: index === currentSlide }"
          ></span>
        </div>
      </div>

      <!-- 로그인 폼 -->
      <div class="login-form">
        <h2>안녕하세요<br>로그인 해주세요</h2>
        <form>
          <input type="text" placeholder="아이디를 입력하세요" />
          <input type="password" placeholder="패스워드를 입력하세요" />
          <button class="button1" type="submit">Login</button>
        </form>
      </div>
    </section>

    <!-- 네비게이션 바 -->
    <nav class="insurance-nav">
      <a href="#travel-insurance">여행자 보험</a>
      <a href="#health-insurance">건강 보험</a>
      <a href="#medical-insurance">실손 의료비 보험</a>
    </nav>

    <!-- 보험 섹션 -->
    <section id="travel-insurance" class="insurance-section">
      <h3>여행자 보험</h3>
      <p>안전한 여행을 위한 준비</p>
      <button class="button2">해외 여행 보험</button>
    </section>

    <section id="health-insurance" class="insurance-section">
      <h3>건강 보험</h3>
      <p>질병에 필요한 보장</p>
      <button class="button2">건강 보험(16~40)</button>
      <button class="button2">건강 보험(41~70)</button>
      <button class="button2">암 보험</button>
    </section>

    <section id="medical-insurance" class="insurance-section">
      <h3>실손 의료비 보험</h3>
      <p>상해/질병 보장</p>
      <button class="button2">실손 의료비 보험</button>
      <button class="button2">유병자 실손 보험</button>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      slides: [
        require('@/assets/images/promotion/tmp1.jpg'),
        require('@/assets/images/promotion/tmp2.jpg'),
        require('@/assets/images/promotion/tmp3.png'),
        require('@/assets/images/promotion/tmp4.png')
      ],
      currentSlide: 0,
      sliderInterval: null
    };
  },

  mounted() {
    this.startSlider();
  },

  methods: {
    startSlider() {
      this.sliderInterval = setInterval(() => {
        this.nextSlide();
      }, 3000);
    },
    nextSlide() {
      this.currentSlide = (this.currentSlide + 1) % this.slides.length;
    },
    pauseSlider() {
      clearInterval(this.sliderInterval);
    },
    resumeSlider() {
      this.startSlider();
    }
  }
};
</script>

<style scoped>
@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
    format('woff');
}

.main-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: 'GmarketSansMedium', Arial, Helvetica, sans-serif;
  font-weight: normal;
  font-style: normal;
}

h2 {
  color: white;
}

input {
  font-size: 14px;
}

.promo-login-section {
  display: flex;
  justify-content: space-between;
  width: 100%;
  background-color: #043873;
  padding: 20px;
}

.promo-slider {
  width: 100%; /* 부모 컨테이너 크기 */
  height: 300px; /* 슬라이더 높이 */
  position: relative;
  overflow: hidden; /* 슬라이더 내부 콘텐츠가 넘치지 않도록 */
}

.promo-image {
  width: 100%;
  height: auto; /* 높이 자동 조정 */
  border-radius: 8px;
  object-fit: cover; /* 이미지를 컨테이너에 맞게 조정 */
}

.pagination {
  position: absolute;
  bottom: 10px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 8px;
}

.pagination span {
  width: 10px;
  height: 10px;
  background-color: white;
  border-radius: 50%;
}

.pagination .active {
  background-color: #4F9CF9;
}

.login-form {
  width: 50%;
  background-color: #C4DEFD;
  padding: 20px;
  border-radius: 8px;
}

.insurance-nav {
  background-color: #043873;
  width: 100%;
  display: flex; /* Flexbox로 변경 */
  justify-content: center; /* 항목 중앙 정렬 */
  gap: 100px; /* 항목 간 간격을 40px로 설정 */
  padding: 10px 0;
}

.insurance-nav a {
  color: white;
  text-decoration: none;
  font-size: 16px;
}

.insurance-section {
  margin: 40px 0;
  text-align: center;
}

.insurance-section h3,
.insurance-section p {
  margin-bottom: 10px;
}

/* 버튼들을 가로로 나열 */
.insurance-section button2 {
  display: inline-block; /* inline-block으로 설정 */
  margin: 15px; /* 버튼 간격 조정 */
}

.insurance-section .button-group {
  display: flex; /* Flexbox로 버튼 그룹 설정 */
  justify-content: center; /* 중앙 정렬 */
  gap: 20px; /* 버튼 간 간격 */
  flex-wrap: wrap; /* 화면이 좁아지면 줄 바꿈 */
}

.button1 {
  background-color: #FFE492;
  color: #043873;
  font-weight: bold;
  padding: 10px 20px;
  margin: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.button2 {
  background-color: #4F9CF9;
  color: white;
  font-weight: bold;
  padding: 20px 15px;
  margin: 30px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  width: 180px; /* 버튼의 너비 */
  height: 60px; /* 버튼의 높이 */
  text-align: left;
  padding-left: 20px;
  display: block;
}

.button2:hover {
  background-color: #4292e3;
}
</style>