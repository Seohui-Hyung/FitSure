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
          v-for="(data, index) in progressData"
          :key="index"
          :class="['circle', { active: isCircleActive(index) }]"
          @click="openModal(index)"
        >
          {{ index + 1 }}
        </div>
      </div>
      <p class="progress-text">현재 {{ currentWeek }}주째 달성 중 🎉</p>

      <!-- 모달 창 -->
      <div v-if="isModalOpen" class="modal-overlay">
        <div class="modal-content">
          <!-- 모달 헤더 -->
          <div class="modal-header">
            <h3>걸음 수 등록</h3>
          </div>

          <!-- 모달 본문 -->
          <div class="modal-body">
            <label class="file-upload">
              <input type="file" @change="uploadImage(currentModalIndex)" />
            </label>
            <div v-if="progressData[currentModalIndex]?.image" class="uploaded-image">
              <img :src="progressData[currentModalIndex].image" alt="Uploaded" />
            </div>
            <div class="input-save-row">
              <input
                id="step"
                type="number"
                v-model.number="progressData[currentModalIndex].steps"
                placeholder="걸음 수 입력"
                required
                class="step-input"
              />
              <button type="button" class="step-save" @click="saveStep">
                저장
              </button>
            </div>
          </div>
          <button class="modal-close" @click="closeModal">닫기</button>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import { ref, reactive } from "vue";

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
    const currentModalIndex = ref(null);
    // const uploadedImage = ref("");

    const progressData = reactive(
      Array.from({ length: 5 }, () => ({
        image: null,
        steps: 0,
      }))
    );

    const isCircleActive = (index) => {
      if (index === 0) {
        // 첫 주차는 조건만 확인
        return progressData[index].steps >= 7000 && progressData[index].image;
      }
      return (
        progressData[index - 1].steps >= 7000 &&
        progressData[index - 1].image &&
        progressData[index].steps >= 7000 &&
        progressData[index].image
      );
    };

    const openModal = (index) => {
      if (index > 0 && !isCircleActive(index - 1)) {
        alert("이전 단계 업로드를 완료해주세요!");
        return;
      }
      currentModalIndex.value = index;
      isModalOpen.value = true;
    };

    const closeModal = () => {
      isModalOpen.value = false;
      currentModalIndex.value = null;
    };

    const uploadImage = (index) => (event) => {
      const file = event.target.files[0];
      if (file) {
        progressData[index].image = URL.createObjectURL(file);
      }
    };

    const saveStep = () => {
      const data = progressData[currentModalIndex.value];
      if (data.steps < 7000) {
        alert("걸음 수가 7000 이상이어야 합니다!");
        return;
      }
      closeModal();
    };

    return {
      currentWeek,
      isModalOpen,
      currentModalIndex,
      progressData,
      openModal,
      closeModal,
      uploadImage,
      saveStep,
      isCircleActive,
    };
  },
};
</script>

<style scoped>
.challenge-progress {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: #c4defd;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  width: 450px;
  height: 180px;
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
  justify-content: space-around;
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
  font-weight: 600;
  color: #043873;
  margin-bottom: 0;
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

.modal-header {
  background-color: #043873;
  color: white;
  padding: 10px;
  border-radius: 8px 8px 0 0;
}

.modal-header h3 {
  color: white;
  margin: 0;
  font-size: 18px;
}

.modal-body {
  padding: 10px;
}

.step-input {
  flex: 1;
  padding: 10px;
  border: 1px solid #043873;
  border-radius: 4px;
}

.step-save {
  background-color: #ffe492;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  color: #043873;
  font-weight: bold;
  transition: background-color 0.3s;
}

.step-save:hover {
  background-color: #ffd54f;
}

.modal-close {
  width: 100px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  margin-top: 10px;
  background-color: #043873;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 300;
  transition: background-color 0.3s;
}

.modal-close:hover {
  background-color: #021c48;
}

.uploaded-image img {
  max-width: 100%;
  margin: 10px 0;
}

.input-save-row {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
}
</style>