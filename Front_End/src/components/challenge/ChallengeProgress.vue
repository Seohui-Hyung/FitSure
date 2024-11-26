<template>
  <div class="challenge-progress">
    <!-- 챌린지 시작 전 상태 -->
    <template v-if="!challengeStarted">
      <div class="challenge-status">
        <div class="inner-box">
          <p>현재 24주 챌린지에 참여하고 있지 않습니다.</p>
        </div>
      </div>
    </template>

    <!-- 챌린지 시작 후 상태 -->
    <template v-else>
      <div class="progress-circles">
        <div
          v-for="n in 5"
          :key="n"
          :class="['circle', { active: n === currentWeek }]"
          @click="openModal(n)"
        >
          {{ n }}
        </div>
      </div>
      <p class="progress-text">현재 {{ currentWeek }}주째 달성 중 🎉</p>

      <!-- 모달 창 -->
      <div v-if="isModalOpen" class="modal-overlay">
        <div class="modal-content">
          <h3>운동 기록 사진</h3>
          <input type="file" @change="uploadImage" />
          <div v-if="uploadedImage" class="uploaded-image">
            <img :src="uploadedImage" alt="Uploaded" />
            <button @click="downloadImage">사진 다운로드</button>
          </div>
          <button @click="closeModal">닫기</button>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  props: {
    challengeStarted: {
      type: Boolean,
      required: true,
    },
  },
  setup(props) {
    const currentWeek = ref(1); // 현재 주차 (DB에서 가져올 예정)
    const isModalOpen = ref(false);
    const uploadedImage = ref("");

    const openModal = (week) => {
      isModalOpen.value = true;
      console.log(`${week}주차 클릭됨`);
    };

    const closeModal = () => {
      isModalOpen.value = false;
    };

    const uploadImage = (event) => {
      const file = event.target.files[0];
      if (file) {
        uploadedImage.value = URL.createObjectURL(file);
      }
    };

    const downloadImage = () => {
      const link = document.createElement("a");
      link.href = uploadedImage.value;
      link.download = "운동기록.png";
      link.click();
    };

    return {
      currentWeek,
      isModalOpen,
      uploadedImage,
      openModal,
      closeModal,
      uploadImage,
      downloadImage,
    };
  },
};
</script>

<style scoped>
.challenge-progress {
  background-color: #c4defd;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  width: 450px;
}

.challenge-status {
  background-color: #c4defd;
  width: 90%;
  height: 200px;
  padding: 20px;
  margin: 10px auto;
  border-radius: 8px;

  display: flex;
  justify-content: center;
  align-items: center;
}

.inner-box {
  background-color: white;
  border-radius: 8px;
  width: 100%;
  height: 90%;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.inner-box p {
  color: #043873;
  font-size: 16px;
  text-align: center;
  margin: 0;
}

.progress-circles {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.circle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  color: #043873;
  cursor: pointer;
  transition: transform 0.3s;
}

.circle.active {
  background-color: #ffe492;
  transform: scale(1.1);
}

.circle:hover {
  background-color: #ffd54f;
}

.progress-text {
  font-size: 16px;
  color: #043873;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
}

.uploaded-image img {
  max-width: 100%;
  margin: 10px 0;
}
</style>