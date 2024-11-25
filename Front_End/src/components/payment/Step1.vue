<template>
    <div class="center-wrapper">
      <!-- 입력 폼 -->
      <div class="form-container">
        <h2>피보험자/계약자 정보를 입력해 주세요</h2>
        <p class="description">피보험자와 계약자가 동일해야 합니다.</p>
        <div class="form-group">
          <label :class="{ active: activeField === 'name' }" for="name">이름</label>
          <input
            id="name"
            type="text"
            placeholder="이름을 입력하세요"
            v-model="name"
            @focus="setActiveField('name')"
            @input="handleInput('name')"
          />
        </div>
        <div v-if="showFields.idNumber" class="form-group">
          <label :class="{ active: activeField === 'idNumber' }" for="idNumberPart1">주민등록번호</label>
          <div class="id-number-inputs">
            <input
              id="idNumberPart1"
              type="text"
              placeholder="앞자리 입력"
              maxlength="6"
              v-model="idNumberPart1"
              @focus="setActiveField('idNumber')"
              @input="handleInput('idNumber')"
            />
            <span style="padding: 0 20px;">-</span>
            <input
              id="idNumberPart2"
              type="password"
              placeholder="뒷자리 입력"
              maxlength="7"
              v-model="idNumberPart2"
              @focus="setActiveField('idNumber')"
            />
          </div>
        </div>
        <div v-if="showFields.email" class="form-group">
          <label :class="{ active: activeField === 'email' }" for="email">이메일</label>
          <input
            id="email"
            type="email"
            placeholder="이메일을 입력하세요"
            v-model="email"
            @focus="setActiveField('email')"
            @input="handleInput('email')"
          />
        </div>
        <!-- 필수 확인 -->
        <div v-if="showFields.confirmation" class="form-group">
          <label for="confirmation">필수 확인</label>
          <button class="modal-button" @click="openModal">[필수] 고객정보 동의</button>
        </div>
      </div>
  
      <!-- 모달 -->
      <div v-if="isModalOpen" class="modal-overlay" @click.self="closeModal">
        <div class="modal-content">
          <div class="modal-header">
            <h3>고객정보 관리 및 가입설계를 위해 개인(신용) 정보처리<br> 동의가 필요합니다. (필수)</h3>
            <button class="close-button" @click="closeModal">✖</button>
          </div>
          <div class="modal-body">
            <div class="checkbox-group">
              <div class="checkbox-item">
                <input
                  type="checkbox"
                  id="agreeAll"
                  v-model="agreeAll"
                  @change="toggleAllAgreements"
                />
                <label for="agreeAll">모두 동의하기</label>
              </div>
              <hr style="padding: 0px; margin: 0;"/>
              <div v-for="(item, index) in agreements" :key="index" class="checkbox-item">
                <input
                  type="checkbox"
                  :id="'agree' + index"
                  v-model="agreements[index].checked"
                  @change="updateAgreeAll"
                />
                <label style="width: 400px;" :for="'agree' + index">{{ item.text }}</label>
                <button class="view-button" @click="viewDetails(index)">보기</button>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button class="cancel-button" @click="closeModal">취소</button>
            <button
              class="confirm-button"
              :disabled="!allAgreementsChecked"
              @click="confirmAgreement"
            >
              확인
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from "vue";
  
  const name = ref("");
  const idNumberPart1 = ref("");
  const idNumberPart2 = ref("");
  const email = ref("");
  
  const activeField = ref("");
  const showFields = ref({
    idNumber: false,
    email: false,
    confirmation: false, // 필수 확인 항목 추가
  });
  
  const isModalOpen = ref(false); // 모달 상태
  const agreeAll = ref(false);
  const agreements = ref([
    { text: "1. 개인(신용)정보의 수집·이용에 관한 사항", checked: false },
    { text: "2. 개인(신용)정보의 제공에 관한 사항", checked: false },
    { text: "3. 개인(신용)정보의 조회에 관한 사항", checked: false },
    { text: "4. 소비자 권익 보호에 관한 사항", checked: false },
  ]);
  
  const openModal = () => {
    isModalOpen.value = true;
  };
  
  const closeModal = () => {
    isModalOpen.value = false;
  };
  
  const handleInput = (field) => {
    if (field === "name" && name.value.trim() !== "") {
      showFields.value.idNumber = true;
    } else if (
      field === "idNumber" &&
      idNumberPart1.value.length === 6 &&
      /^[0-9]+$/.test(idNumberPart1.value)
    ) {
      showFields.value.email = true;
    } else if (field === "email" && email.value.trim() !== "") {
      showFields.value.confirmation = true;
    }
  };
  
  const toggleAllAgreements = () => {
    agreements.value.forEach((item) => (item.checked = agreeAll.value));
  };
  
  const updateAgreeAll = () => {
    agreeAll.value = agreements.value.every((item) => item.checked);
  };
  
  const confirmAgreement = () => {
    console.log("동의 완료!");
    closeModal();
  };
  
  const viewDetails = (index) => {
    alert(`${agreements.value[index].text} 상세내용 보기`);
  };
  
  const allAgreementsChecked = computed(() => {
    return agreements.value.every((item) => item.checked);
  });
  </script>
  
  <style>
  .center-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    /* min-height: 100vh; */
  }
  
  .form-container {
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
    width: 800px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .description {
    font-size: 14px;
    color: #666;
    margin-bottom: 30px;
  }
  
  .form-group {
    display: flex;
    align-items: center;
    margin-bottom: 25px;
  }
  
  label {
    width: 160px;
    font-size: 17px;
    color: black;
  }
  
  input {
    flex: 1;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    color: #000000b4;
  }
  
  .modal-button {
    padding: 10px 20px;
    font-size: 14px;
    background-color: #011d3b57;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .modal-button:hover {
    background-color: #0056b3;
  }


/* 모달 관련 스타일 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%; /* 화면 전체 너비 */
  height: 100%; /* 화면 전체 높이 */
  background-color: rgba(0, 0, 0, 0.438); /* 어두운 회색 배경 */
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  z-index: 1000; /* 다른 요소 위에 표시 */
}

.modal-content {
  position: relative;
  background-color: #ffffff !important; /* 흰색 배경 */
  padding: 25px;
  border-radius: 8px;
  width: 100%; /* 기본 너비 */
  max-width: 600px; /* 최대 너비 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* 그림자 효과 */
}

.modal-header h3 {
  margin: 0;
  font-size: 22px;
  color: #333;
  text-align: left;
}

.modal-body {
  margin-top: 20px;
  text-align: left; /* 글씨 왼쪽 정렬 */
  font-size: 16px;
  line-height: 1.5;
}

.checkbox-group {
  display: flex;
  flex-direction: column;
  gap: 15px; /* 체크박스 간 여백 */
}

.checkbox-item label {
  margin-left: 8px;
  margin-bottom: 7px;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
  gap: 10px; /* 버튼 간 여백 */
}

.cancel-button {
  background-color: #cf0000;
  color: #ffffff;
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.confirm-button {
  background-color: #0056b3;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.confirm-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

/* 보기 버튼 스타일 */
.view-button {
  background-color: #007bff; /* 파란색 배경 */
  color: white;
  padding: 5px 15px; /* 버튼 크기 조정 */
  font-size: 14px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease; /* 마우스 오버 애니메이션 */
}

.view-button:hover {
  background-color: #0056b3; /* 호버 시 더 진한 파란색 */
}

.checkbox-item input[type="checkbox"] {
    appearance: none; /* 기본 체크박스 스타일 제거 */
    width: 10px; /* 체크박스 너비 */
    height: 10px; /* 체크박스 높이 */
    border: 2px solid #FFE492; /* 테두리 색상 */
    border-radius: 4px; /* 모서리를 둥글게 */
    outline: none; /* 포커스 아웃라인 제거 */
    cursor: pointer; /* 마우스 커서 변경 */
    display: inline-block;
    position: relative;
    vertical-align: middle; /* 텍스트와 정렬 */
}

.checkbox-item input[type="checkbox"]:checked {
    background-color: #FFE492; /* 체크 시 배경 색상 */
    border-color: #FFE492; /* 체크 시 테두리 색상 */
}

.checkbox-item input[type="checkbox"]:checked::after {
    content: "✔"; /* 체크 표시 */
    font-size: 16px; /* 체크 표시 크기 */
    color: #ffffff; /* 체크 표시 색상 */
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

</style>
  