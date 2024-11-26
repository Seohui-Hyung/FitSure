<template>
  <div class="profile-page">
    <!-- 사용자 정보 섹션 -->
    <section class="user-info">
      <div class="user-details">
        <h2>{{ username }}</h2>
        <div class="details-row">
          <RouterLink to="/auth/info-change">개인 정보 수정</RouterLink> | 보유 쿠폰 1개
        </div>
        <ChallengeProgress :challengeStarted="challengeStarted" />
      </div>
      <div class="challenge-box">
        <!-- 챌린지 시작하기 버튼 또는 로드맵 이미지 -->
        <template v-if="!challengeStarted">
          <button class="challenge-button" @click="startChallenge">
            24주 챌린지 시작하기
          </button>
          <ul class="challenge-info">
            <br />
            <a>▶ </a>
            <a style="text-decoration: underline;">24주 챌린지란?</a>
            <li>24주간 매주 5일 이상 7000보 이상 걷는 챌린지입니다.</li>
            <li>(연속으로 달성하지 못할 시 카운트가 초기화됩니다.)</li>
            <br />
            <a>▶ </a>
            <a style="text-decoration: underline;">달성 시 혜택</a>
            <li>FITSURE의 보험 상품 하나를 6개월간 5% 할인된 가격에</li>
            <li>이용 가능한 쿠폰을 제공해드립니다.</li>
          </ul>
        </template>
        <template v-else>
          <img src="/public/images/road.png" alt="로드맵" class="road-image" />
        </template>
      </div>
    </section>

    <!-- 보험 가입 내역 섹션 -->
    <section class="insurance-section">
      <h3>보험 가입 내역</h3>
      <p>가입한 보험이 없습니다.</p>
    </section>

    <!-- 과거 보험 가입 내역 섹션 -->
    <section class="insurance-section">
      <h3>과거 보험 가입 내역</h3>
      <p>과거 보험 가입 내역이 없습니다.</p>
    </section>
  </div>
</template>

<script>
import { ref } from "vue";
import { useUserStore } from "@/store/useUserStore";
import ChallengeProgress from "@/components/challenge/ChallengeProgress.vue";

export default {
  components: {
    ChallengeProgress,
  },
  setup() {
    const userStore = useUserStore();
    const username = userStore.username;
    const challengeStarted = ref(false);

    const startChallenge = () => {
      challengeStarted.value = true;
    };

    const logout = () => {
      userStore.logout(); // 로그아웃 함수 호출
      window.location.href = '/'; // 로그아웃 후 메인 페이지로 이동
    };

    return {
      username,
      challengeStarted,
      startChallenge,
      logout,
    };
  },
};
</script>

<style scoped>
body {
  margin: 0;
  padding: 0;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
}

.profile-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 30px;
  padding: 20px;
}

.user-info {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  background-color: #043873;
  color: white;
  width: 100%;
  max-width: 1200px;
  padding: 20px;
  border-radius: 8px;
}

.details-row {
  display: flex; /* Flexbox로 구성 */
  align-items: center; /* 수직 중앙 정렬 */
  margin-bottom: 20px; /* ChallengeProgress와 간격 추가 */
  margin-left: 20px;
}

.details-row a {
  color: #ffffff;
  text-decoration: none;
}

.details-row a:hover {
  text-decoration: underline;
}

.details-row p {
  margin: 0; /* 기본 마진 제거 */
  margin-left: 30px;
  text-align: left; /* 좌측 정렬 */
  font-size: 16px; /* 글자 크기 조정 */
  white-space: nowrap; /* 텍스트를 한 줄로 출력 */
}

.challenge-progress {
  margin: 10px; /* 필요에 따라 조정 */
}

.profile-picture {
  flex-shrink: 0; /* 사진 크기 고정 */
  margin-right: 20px; /* 텍스트와 간격 추가 */
}

.profile-picture img {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border: 3px solid #c4defd;
}

.user-details {
  flex: 1;
  margin-bottom: 0px;
}

.user-details p {
    text-align: right;
}

.user-details h2 {
  margin: 0;
  font-size: 24px;
}

.challenge-status {
  background-color: #c4defd;
  width: 90%;
  height: 200px;
  padding: 20px;
  margin: 10px;
  border-radius: 8px;
}

.challenge-status p {
    text-align: center;
    margin-bottom: 0px;
}

.challenge-box {
  background-color: white;
  color: #043873;
  width: 50%;
  padding: 20px;
  border-radius: 8px;
  text-align: left;
  margin: 10px;
}

.challenge-button {
  background-color: #ffe492;
  color: #043873;
  padding: 10px 20px;
  width: 100%;
  height: 90px;
  border: none;
  border-radius: 8px;
  font-size: 24px;
  font-weight: 800;
  cursor: pointer;
  margin-bottom: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3); /* 그림자 추가 */
  transition: box-shadow 0.3s ease; /* 호버 시 부드러운 전환 */
}

.challenge-button:hover {
  background-color: #ffd54f;
}

.challenge-info {
  list-style: none;
  padding: 0;
  margin: 0;
}

.challenge-info a {
  font-weight: 600;
}

.challenge-info li {
  margin-bottom: 5px;
}

.road-image {
  width: 100%;
  border-radius: 8px;
  object-fit: cover;
}

.insurance-section {
  width: 100%;
  max-width: 1200px;
  position: relative;

  margin-left: 10px;
}

.insurance-section h3 {
  position: relative;
  display: inline-block;
  font-size: 20px;
  font-weight: 500;
  margin-bottom: 10px;
  padding-left: 5px;
  color: #043873;
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

.insurance-section p {
  margin: 0;
  font-size: 16px;
  padding-left: 5px;
  color: #777;
}
</style>