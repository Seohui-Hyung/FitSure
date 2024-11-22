<template>
  <div class="insurance-form-page">
    <h3 class="form-title">보험 수정</h3>
    <hr />
    <div class="form-container">
      <form @submit.prevent="handleSubmit">
        <!-- 보험 종류 -->
        <div class="form-row">
          <label for="insurance-type" class="form-label">보험 종류</label>
          <select id="insurance-type" v-model="formData.insuranceType" class="form-input">
            <option value="" disabled>보험 종류 선택</option>
            <option value="1">여행자 보험</option>
            <option value="2">건강 보험</option>
            <option value="3">실손 보험</option>
          </select>
        </div>
        <!-- 보험 이름 -->
        <div class="form-row">
          <label for="insurance-name" class="form-label">보험 이름</label>
          <input
            id="insurance-name"
            type="text"
            v-model="formData.insuranceName"
            class="form-input"
            placeholder="보험 이름을 입력해주세요"
          />
        </div>
        <!-- 가격 -->
        <div class="form-row">
          <label for="premium" class="form-label">가격</label>
          <input
            id="premium"
            type="number"
            v-model="formData.premium"
            class="form-input"
            placeholder="금액을 입력해주세요"
          />
        </div>
        <!-- 설명 파일 첨부 -->
        <div class="form-row">
          <label for="description-file" class="form-label">설명 파일 첨부</label>
          <input
            id="description-file"
            type="file"
            @change="handleFileChange"
            class="form-input"
          />
        </div>
        <!-- 운영 여부 -->
        <div class="form-row">
          <label for="validity" class="form-label">운영 여부</label>
          <select id="validity" v-model="formData.validity" class="form-input">
            <option value="" disabled>운영 여부</option>
            <option value="1">운영</option>
            <option value="0">미운영</option>
          </select>
        </div>
        <!-- 제출 버튼 -->
        <div class="form-row submit-button-container">
          <button type="submit" class="submit-button">수정 완료</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useAdminStore } from "@/store/useAdminStore";

const route = useRoute(); // 라우터 정보 가져오기
const router = useRouter(); // 라우터 사용
const store = useAdminStore(); // 스토어 사용

const formData = reactive({
  insuranceType: 0,
  insuranceName: "",
  premium: null,
  file: null,
  validity: "",
});

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    formData.file = file;
  }
};

const fetchInsuranceData = (insuranceId) => {
  try {
    store.detailInsurance(insuranceId)
    .then((insurance)=>{
        console.log(insurance)
        formData.insuranceType = insurance.insuranceType;
        formData.insuranceName = insurance.insuranceName;
        formData.premium = insurance.premium; // 필드 이름 수정
        formData.validity = insurance.validity.toString(); // 숫자를 문자열로 변환
        formData.file = insurance.detail
    });

  } catch (error) {
    console.error("보험 데이터를 가져오는 중 오류 발생:", error);
  }
};

onMounted(() => {
  const insuranceId = route.params.insuranceId;
  if (insuranceId) {
    fetchInsuranceData(insuranceId);
  }
});

const handleSubmit = async () => {
  if (!formData.insuranceType || !formData.premium || !formData.validity) {
    alert("모든 필드를 입력하세요.");
    return;
  }
  try {
    await store.updateInsurance(route.params.insuranceId, formData, formData.file);

    alert("보험 수정이 완료되었습니다!");
    router.push({ name: "InsuranceList" }); // 수정 완료 후 목록으로 이동
  } catch (error) {
    console.error("수정 요청 중 오류 발생:", error);
    alert("보험 수정 중 문제가 발생했습니다.");
  }
};


</script>

<style scoped>
/* 기존 스타일 유지 */
.insurance-form-page {
  padding: 20px;
  background-color: #f9f9f9; 
  min-height: 90vh; 
  display: flex;
  flex-direction: column; 
  align-items: center; 
  justify-content: flex-start;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.form-container {
  background-color: #ffffff; /* 흰색 배경 */
  border-radius: 10px; /* 둥근 모서리 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  padding: 30px; /* 내부 여백 */
  width: 100%; /* 가로 크기를 부모 요소에 맞춤 */
  max-width: 1800px; /* 최대 너비 설정 */
}

.form-title {
  margin-bottom: 5px;
  text-align: left;
  width: 100%; 
  color: #000000;
}

hr {
  margin-bottom: 20px;
  width: 100%;
}

.form-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.form-label {
  flex: 1;
  font-weight: bold;
  margin-right: 20px;
  text-align: left;
  font-size: 16px;
}

.form-input {
  flex: 2;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
  box-sizing: border-box;
}

.submit-button-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.submit-button {
  background-color: #043873;
  color: white;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #0056b3;
}
</style>
