<template>
  <div class="main-container">
    <!-- 홍보 자료 슬라이더와 로그인 폼 -->
    <section v-if="slides.length > 0" class="promo-login-section">
      <!-- 홍보 슬라이더 -->
      <div class="promo-slider" @mouseenter="pauseSlider" @mouseleave="resumeSlider">
        <img :src="slides[currentSlide]" alt="Promo Image" class="promo-image" />
        <div class="pagination">
          <span 
            v-for="(slide, index) in slides" 
            :key="index" 
            :class="{ active: index === currentSlide }"
            @click="goToSlide(index)"
          ></span>
        </div>
      </div>

      <!-- 로그인 폼 -->
      <div class="login-form">
        <h2>안녕하세요<br>로그인 해주세요</h2>
        <form class="login-form-container">
          <div class="input-container">
            <input type="text" placeholder="아이디를 입력하세요" />
            <input type="password" placeholder="패스워드를 입력하세요" />
          </div>
          <button class="button1" type="submit">Login</button>
        </form>
      </div>
    </section>

    <!-- 네비게이션 바 -->
    <nav class="insurance-nav">
      <a href="#travel-insurance" @click.prevent="scrollToSection('travel-insurance')">여행자 보험</a>
      <a href="#health-insurance" @click.prevent="scrollToSection('health-insurance')">건강 보험</a>
      <a href="#medical-insurance" @click.prevent="scrollToSection('medical-insurance')">실손 의료비 보험</a>
    </nav>

    <!-- 보험 섹션 -->
    <section id="travel-insurance" class="insurance-section">
      <h3>여행자 보험</h3>
      <p>안전한 여행을 위한 준비</p>
      <button class="button2">
        <span class="button-title">해외 여행 보험</span>
        <br>
        <span class="button-description">분실~ 병원~ 전부 보장!</span>
      </button>
    </section>

    <section id="health-insurance" class="insurance-section">
      <h3>건강 보험</h3>
      <p>질병에 필요한 보장, 최대 100세까지 든든하게</p>
      <button class="button2">
        <span class="button-title">건강 보험(16~40)</span>
        <br>
        <span class="button-description">고혈압, 당뇨 특약</span>
      </button>
      <button class="button2">
        <span class="button-title">건강 보험(41~70)</span>
        <br>
        <span class="button-description">고혈압, 당뇨 특약</span>
      </button>
      <button class="button2">
        <span class="button-title">암 보험</span>
        <br>
        <span class="button-description">췌장암, 대장암, 유방암 보장</span>
      </button>
    </section>

    <section id="medical-insurance" class="insurance-section">
      <h3>실손 의료비 보험</h3>
      <p>상해/질병 보장</p>
      <button class="button2">
        <span class="button-title">실손 의료비 보험</span>
        <br>
        <span class="button-description">이것저것 다 보장!</span>
      </button>
      <button class="button2">
        <span class="button-title">유병자 실손 보험</span>
        <br>
        <span class="button-description">병이 있나?? 이거 하라</span>
      </button>
    </section>

    <section class="consultation-section">
      <div class="consultation-content">
        <h2>상담을 원하십니까?</h2>
        <p>당신을 위한 맞춤형 상담 제공!</p>
        <p>지금 시작하세요!</p>
        <button class="consultation-button">여길 클릭하세요 ></button>
      </div>
      <div class="consultation-image">
        <img src="/images/promotion/tmp1.jpg" alt="상담 이미지">
      </div>
    </section>

    <section class="testimonial-section">
      <h2>고객들의 이용 후기</h2>
      <div class="underline"></div>
      <div class="testimonial-slider">
        <div 
          v-for="(testimonial, index) in testimonials" 
          :key="index" 
          v-show="index === currentTestimonial"
        >
          <blockquote>{{ testimonial.text }}</blockquote>
          <p>{{ testimonial.name }}</p>
        </div>
      </div>
      <div class="pagination">
        <span 
          v-for="(testimonial, index) in testimonials" 
          :key="index" 
          :class="{ active: index === currentTestimonial }"
          @click="goToTestimonial(index)"
        ></span>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      slides: [
        '/images/promotion/tmp1.jpg',
        '/images/promotion/tmp2.jpg',
        '/images/promotion/tmp3.png',
        '/images/promotion/tmp4.png',
      ],
      currentSlide: 0,
      sliderInterval: null,
      testimonials: [
      { text: "여러 보험을 한눈에 모아주고 맞춤형 추천까지 해주는 점이 너무 좋아요!", name: "YOONJUNE, UM" },
      { text: "직관적이고 사용하기 편리합니다. 500만원을 아꼈습니다!", name: "JIEUN LEE, IU" },
      { text: "비대면이라 가서 직접 계약하지 않아도 되니 편했어요!", name: "BYUNGHEE, YOON" }
    ],
    currentTestimonial: 0
    };
  },

  mounted() {
    this.startSlider();
  },

  methods: {
    startSlider() {
      this.sliderInterval = setInterval(() => {
        this.nextSlide();
      }, 5000);
    },
    nextSlide() {
      this.currentSlide = (this.currentSlide + 1) % this.slides.length;
    },
    pauseSlider() {
      clearInterval(this.sliderInterval);
    },
    resumeSlider() {
      this.startSlider();
    },
    goToSlide(index) { // 클릭한 동그라미에 해당하는 이미지로 이동
      this.pauseSlider(); // 슬라이더 자동 재생 중단
      this.currentSlide = index; // 현재 슬라이드 업데이트
      this.resumeSlider(); // 슬라이더 재생 재개
    },
    scrollToSection(sectionId) {
    const section = document.getElementById(sectionId); // 섹션 ID로 요소 찾기
    if (section) {
      const offset = section.getBoundingClientRect().top + window.pageYOffset; // 섹션의 Y 좌표 계산
      const offsetMiddle = offset - (window.innerHeight / 2) + (section.offsetHeight / 2); // 화면 중앙에 맞추기
      window.scrollTo({
        top: offsetMiddle, // 중앙 위치로 스크롤
        behavior: 'smooth' // 부드럽게 이동
      });
    }
    },
    goToTestimonial(index) {
      this.currentTestimonial = index; // 현재 후기 업데이트
    },
    nextTestimonial() {
      this.currentTestimonial = (this.currentTestimonial + 1) % this.testimonials.length;
    },
    prevTestimonial() {
      this.currentTestimonial = (this.currentTestimonial - 1 + this.testimonials.length) % this.testimonials.length;
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
  align-items: flex-start;
  padding-left: 20px;
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
  align-items: stretch;
  width: 100%;
  background-color: #043873;
  padding: 20px;
  height: 300px;
}

.promo-slider {
  width: 100%; /* 부모 컨테이너 크기 */
  max-width: 50%;
  height: 100%; /* 슬라이더 높이 */
  position: relative;
  overflow: hidden; /* 슬라이더 내부 콘텐츠가 넘치지 않도록 */
  flex: 1;
}

.promo-image {
  width: 100%;
  height: 100%; /* 높이 자동 조정 */
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
  justify-content: center;
  margin-top: 20px;
}

.pagination span {
  width: 10px;
  height: 10px;
  background-color: white;
  border-radius: 50%;
  cursor: pointer;
}

.pagination .active {
  background-color: #4F9CF9;
}

.login-form {
  width: 50%;
  max-width: 50%;
  background-color: #C4DEFD;
  padding: 20px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex: 1;
}

.login-form-container {
  display: flex; /* 입력창과 버튼을 가로로 정렬 */
  align-items: stretch; /* 입력창과 버튼의 높이를 동일하게 설정 */
  gap: 10px; /* 입력창과 버튼 사이의 간격 */
}

.input-container {
  display: flex; /* 입력창들을 수직으로 정렬 */
  flex-direction: column;
  gap: 5px; /* 입력창 사이 간격 */
  flex: 1; /* 버튼과 균형을 맞추기 위해 공간을 차지 */
}

.input-container input {
  padding: 10px; /* 내부 여백 */
  font-size: 14px; /* 폰트 크기 */
  border: 1px solid #ccc; /* 테두리 색상 */
  border-radius: 4px; /* 테두리 둥글게 */
  width: 100%; /* 입력창 너비를 부모 컨테이너에 맞춤 */
  box-sizing: border-box;
}

.insurance-nav {
  background-color: #043873;
  width: 100%;
  display: flex; /* Flexbox로 변경 */
  justify-content: center; /* 항목 중앙 정렬 */
  gap: 100px; /* 항목 간 간격을 40px로 설정 */
  padding: 20px 0;
}

.insurance-nav a {
  color: white;
  text-decoration: none;
  font-size: 16px;
}

.insurance-section {
  margin: 40px 0;
  text-align: left;
  padding-left: 20px;
}

.insurance-section h3 {
  position: relative; /* 가상 요소 배치를 위해 상대 위치 설정 */
  display: inline-block; /* 제목 너비에 맞게 가상 요소 크기 조정 */
  font-size: 24px; /* 제목 폰트 크기 */
  margin-bottom: 20px; /* 제목과 이미지 사이 간격 */
}

.insurance-section h3::after {
  content: ''; /* 가상 요소 생성 */
  position: absolute; /* 제목 밑에 위치 설정 */
  bottom: 0px; /* 제목 아래에 위치 */
  left: 0;
  width: 250%; /* 제목 너비에 맞게 이미지 크기 조정 */
  height: 12px; /* 이미지 높이 */
  background-image: url('/images/deco/underline.png'); /* 이미지 경로 */
  background-size: contain; /* 이미지 크기 조정 */
  background-repeat: no-repeat; /* 이미지 반복 제거 */
}

.insurance-section h3,
.insurance-section p {
  margin-bottom: 10px;
}

/* 버튼들을 가로로 나열 */
.insurance-section button2 {
  display: inline-block; /* inline-block으로 설정 */
  margin: 10px; /* 버튼 간격 조정 */
}

.insurance-section .button-group {
  display: flex; /* Flexbox로 버튼 그룹 설정 */
  justify-content: flex-start;
  gap: 20px; /* 버튼 간 간격 */
  flex-wrap: wrap; /* 화면이 좁아지면 줄 바꿈 */
}

.button1 {
  background-color: #FFE492; /* 버튼 배경색 */
  color: #043873; /* 텍스트 색상 */
  font-weight: bold; /* 텍스트 굵기 */
  padding: 0; /* 내부 여백 제거 */
  border: none; /* 테두리 제거 */
  border-radius: 4px; /* 모서리를 둥글게 설정 */
  cursor: pointer; /* 마우스 포인터 변경 */
  width: 80px; /* 버튼 너비 */
  height: 100%;
  display: flex; /* Flexbox로 텍스트 정렬 */
  justify-content: center; /* 텍스트를 가로 중앙 정렬 */
  align-items: center; /* 텍스트를 세로 중앙 정렬 */
  transition: background-color 0.3s ease, transform 0.2s ease; /* 호버 시 부드러운 전환 효과 */
}

.button1:hover {
  background-color: #FFD54F; /* 호버 시 배경색 변경 */
  transform: translateY(-2px); /* 약간의 부드러운 위로 이동 효과 */
}

.button2 {
  background-color: #4F9CF9;
  color: white;
  font-weight: bold;
  padding: 20px 15px;
  margin: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  width: 180px; /* 버튼의 너비 */
  height: 60px; /* 버튼의 높이 */
  text-align: left;
  padding-left: 20px;
  padding-bottom: 80px;
  display: inline-block;
}

.button2:hover {
  background-color: #4292e3;
}

.button-title {
  font-weight: bold;
  font-size: 14px;
}

.button-description {
  font-size: 10px;
  color: #fff; /* 설명 텍스트 색상 */
  opacity: 0.8; /* 약간 연하게 표시 */
}

.consultation-section {
  display: flex; /* Flexbox로 좌우 정렬 */
  justify-content: space-between; /* 왼쪽 문구와 오른쪽 이미지를 나눔 */
  align-items: center; /* 세로 중앙 정렬 */
  background-color: #043873; /* 파란 배경 */
  color: white; /* 텍스트 색상 */
  padding: 40px 20px; /* 섹션 안쪽 여백 */
  gap: 20px; /* 문구와 이미지 사이 간격 */
  width: 100%;
  box-sizing: border-box;
}

.consultation-content {
  flex: 1; /* 문구가 공간을 차지 */
  padding-left: 50px;
}

.consultation-content h2 {
  font-size: 28px;
  margin-bottom: 10px;
}

.consultation-content p {
  font-size: 15px;
  margin-bottom: 10px;
}

.consultation-button {
  background-color: #FFE492; /* 버튼 배경색 */
  color: #043873; /* 버튼 텍스트 색상 */
  font-weight: bold;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.consultation-button:hover {
  background-color: #FFD54F; /* 호버 시 색상 변경 */
}

.consultation-image {
  flex: 1; /* 이미지가 공간을 차지 */
  display: flex;
  justify-content: center;
}

.consultation-image img {
  width: 200px;
  max-width: 100%;
  height: 200px;
  border-radius: 0px; /* 둥근 모서리 */
}

.testimonial-section {
  background-color: #fff; /* 하얀 배경 */
  text-align: center;
  padding: 40px 20px;
  position: relative;
  width: 100%;
}

.testimonial-section h2 {
  font-size: 28px;
  color: #043873;
  margin-bottom: 10px;
}

.underline {
  width: 80px;
  height: 4px;
  background-color: #FFD54F; /* 하이라이트 색상 */
  margin: 0 auto 20px auto;
}

.testimonial-slider {
  display: flex;
  overflow: hidden; /* 슬라이더 부분만 표시 */
  gap: 20px;
  justify-content: center;
}

.testimonial {
  flex: 0 0 300px; /* 각 슬라이더 너비 */
  background-color: #C4DEFD; /* 카드 배경 */
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.testimonial blockquote {
  font-size: 16px;
  font-style: italic;
  margin-bottom: 10px;
}

.testimonial p {
  font-size: 14px;
  color: #043873;
  font-weight: bold;
  margin-top: 0;
}

.pagination {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
}

.pagination span {
  width: 12px;
  height: 12px;
  background-color: #ddd;
  border-radius: 50%;
  cursor: pointer;
}

.pagination .active {
  background-color: #043873; /* 활성화된 페이지네이션 색상 */
}
</style>