<!-- 모든 페이지에서 공통으로 사용되는 헤더 -->
<template>
    <header class="header">
      <nav class="nav">
        <RouterLink to="/" class="logo"></RouterLink>
        
        <!-- 보험 소개 메뉴 -->
        <div class="nav-item" @mouseenter="toggleMenu('insurance')" @mouseleave="toggleMenu(null)">
          <RouterLink to="#">보험 소개</RouterLink>
          <transition name="fade">
            <div v-show="activeMenu === 'insurance'" class="dropdown">
              <ul>
                <li><RouterLink to="/insurance-detail">여행자 보험</RouterLink></li>
                <li><RouterLink to="/insurance-detail">건강 보험</RouterLink></li>
                <li><RouterLink to="/insurance-detail">실손 보험</RouterLink></li>
              </ul>
            </div>
          </transition>
        </div>
        
        <!-- 이용 안내 메뉴 -->
        <div class="nav-item" @mouseenter="toggleMenu('guide')" @mouseleave="toggleMenu(null)">
          <RouterLink to="#">이용 안내</RouterLink>
          <transition name="fade">
            <div v-show="activeMenu === 'guide'" class="dropdown">
              <ul>
                <li><RouterLink to="/board">공지사항</RouterLink></li>
                <li><RouterLink to="/live-support">실시간 상담 연결</RouterLink></li>
                <li><RouterLink to="/challenge">24주 챌린지</RouterLink></li>
              </ul>
            </div>
          </transition>
        </div>
        
        <!-- 마이 페이지 메뉴 -->
        <div class="nav-item" @mouseenter="toggleMenu('mypage')" @mouseleave="toggleMenu(null)">
          <RouterLink to="#">마이 페이지</RouterLink>
          <transition name="fade">
            <div v-show="activeMenu === 'mypage'" class="dropdown">
              <ul>
                <li><RouterLink to="/mypage">회원 정보</RouterLink></li>
              </ul>
            </div>
          </transition>
        </div>
  
        <!-- 로그인/회원가입 버튼 -->
        <div class="nav-actions">
          <button @click="handleLogin">로그인/회원가입</button>
        </div>
      </nav>
    </header>
  </template>
  

<script>
import { ref } from 'vue';
import { RouterView, RouterLink } from 'vue-router';

export default {
  components: { RouterView, RouterLink },
  setup() {
    const activeMenu = ref(null);
    const isAuthenticated = ref(false);

    const toggleMenu = (menu) => {
      activeMenu.value = menu;
    };

    return {
      activeMenu,
      isAuthenticated,
      toggleMenu,
    };
  },
  methods: {
    handleLogin() {
      console.log("Login clicked");
    },
  },
};
</script>
  
<style scoped>
@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
    format('woff');
}

body, html {
  width: 100%; /* 전체 화면을 채우도록 설정 */
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.header {
  background-color: #043873;
  color: white;
  padding: 20px 30px;
  position: relative;
  font-family: 'GmarketSansMedium', Arial, Helvetica, sans-serif;
  font-weight: normal;
  font-style: normal;
  width: 100%;
  height: auto; /* 헤더 높이를 내용에 맞게 자동 조정 */
  max-width: none;
  min-height: 80px; /* 최소 높이를 설정 */
}

.logo {
  width: 150px;
  height: 40px;
  background-image: url('@/assets/images/Fitsure_main.png');
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

.nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap; /* 줄바꿈을 허용 */
  width: 100%;
  gap: 20px;
}

.nav-menu {
  list-style: none;
  display: flex;
  gap: 20px;
  margin: 0;
  padding: 0;
}

.nav-item {
  position: relative;
}

.nav-item a {
  color: white;
  text-decoration: none;
  padding: 10px 15px;
  display: block;
}

.nav-actions button {
  background-color: #FFE492; /* 버튼 배경색 */
  color: #043873; /* 버튼 텍스트 색상 */
  border: none;
  padding: 10px 20px;
  font-size: 12px;
  font-weight: bolder;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.nav-actions button:hover {
  background-color: #ffda70; /* 호버 시 색상 */
}

.dropdown {
  background-color: rgba(4, 56, 115, 0.4);
  position: absolute;
  top: 100%;
  left: 0;
  padding: 15px;
  border-radius: 4px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  opacity: 0;
  visibility: hidden;
  transform: translateY(-10px);
  transition: opacity 0.3s ease, transform 0.3s ease;
  width: 130px;
}

.dropdown ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.dropdown li {
  margin: 10px 0;
  font-size: 10px;
}

.dropdown a {
  color: white;
  text-decoration: none;
  padding: 10px 20px;
  display: block;
  width: 100%;
}

.nav-item:hover .dropdown {
  opacity: 1;
  visibility: visible;
  transform: translateY(0);
}

/* 반응형 스타일 */
@media (min-width: 1024px) {
  .header {
    padding: 20px 40px; /* 큰 화면에서 패딩 증가 */
    min-height: 120px; /* 헤더 최소 높이 증가 */
    width: 100%;
    max-width: none;
  }

  .logo {
    width: 180px; /* 로고 크기 증가 */
    height: 80px;
  }

  .nav-item a {
    font-size: 16px; /* 텍스트 크기 증가 */
  }

  .nav-actions button {
    padding: 20px 30px; /* 버튼 크기 증가 */
    font-size: 16px; /* 텍스트 크기 증가 */
  }

  .dropdown {
    width: 400px; /* 큰 화면에서 폭 증가 */
  }
}

@media (max-width: 768px) {
  .nav {
    flex-direction: column; /* 세로 정렬로 변경 */
    align-items: flex-start;
  }

  .nav-menu {
    flex-direction: column; /* 메뉴를 세로로 나열 */
    gap: 10px; /* 간격 조정 */
  }

  .nav-actions {
    margin-top: 10px; /* 로그인 버튼 아래로 정렬 */
  }

  .dropdown {
    position: static; /* 작은 화면에서 드롭다운 위치 고정 */
    transform: none;
    box-shadow: none;
    width: 100%;
    opacity: 1;
    visibility: visible; /* 항상 보이도록 */
    background-color: rgba(4, 56, 115, 0.7); /* 약간 더 투명하게 */
  }

  .dropdown ul {
    display: flex;
    flex-direction: column; /* 세로 정렬 */
    gap: 10px;
  }
}

@media (max-width: 480px) {
  .logo {
    width: 80px; /* 로고 크기 조정 */
    height: 40px;
  }

  .nav-actions button {
    font-size: 12px; /* 버튼 크기 줄이기 */
    padding: 8px 15px;
  }

  .nav-item a {
    padding: 8px 12px; /* 메뉴 간격 줄이기 */
  }
}
</style>