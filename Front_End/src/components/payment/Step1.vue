<template>
    <div>
      <!-- 입력 폼 -->
      <div>
        <h2>피보험자/계약자 정보를 입력해 주세요</h2>
        <p>피보험자와 계약자가 동일해야 합니다.</p>
        <div class="form-group">
          <label :class="{ active: activeField === 'name' }">이름</label>
          <input
            type="text"
            placeholder="이름을 입력하세요"
            v-model="name"
            @focus="setActiveField('name')"
            @input="handleInput('name')"
          />
        </div>
        <div v-if="showFields.idNumber" class="form-group">
          <label :class="{ active: activeField === 'idNumber' }">주민등록번호</label>
          <div class="id-number-inputs">
            <input
              type="text"
              placeholder="앞자리 입력"
              maxlength="6"
              v-model="idNumberPart1"
              @focus="setActiveField('idNumber')"
              @input="handleInput('idNumber')"
            />
            -  
            <input
              type="password"
              placeholder="뒷자리 입력"
              maxlength="7"
              v-model="idNumberPart2"
              @focus="setActiveField('idNumber')"
            />
          </div>
        </div>
        <div v-if="showFields.email" class="form-group">
          <label :class="{ active: activeField === 'email' }">이메일</label>
          <input
            type="email"
            placeholder="이메일을 입력하세요"
            v-model="email"
            @focus="setActiveField('email')"
            @input="handleInput('email')"
          />
        </div>
      </div>
  
      <!-- 확인 버튼 -->
      <div v-if="allFieldsCompleted" class="button-container">
        <h3>필수 확인 사항</h3>
        <button @click="openModal">확인</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  
  const name = ref("");
  const idNumberPart1 = ref("");
  const idNumberPart2 = ref("");
  const email = ref("");
  
  const activeField = ref("");
  const showFields = ref({
    idNumber: false,
    email: false,
  });
  const allFieldsCompleted = ref(false);
  
  const setActiveField = (field) => {
    activeField.value = field;
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
    allFieldsCompleted.value = true;
  }
};

  </script>
  
  <style>
  /* 기본 레이아웃 스타일 */
  div {
    margin-bottom: 24px;
  }
  
  .form-group {
    display: flex;
    align-items: center;
    margin-left: 25px;
  }
  
  label {
    width: 150px; /* 레이블의 고정 너비 */
    font-size: 14px;
    color: black;
    transition: color 0.3s;
  }
  
  label.active {
    color: blue;
  }
  
  input {
    flex: 1; 
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .id-number-inputs {
    display: flex;
    align-items: center;
    margin: 0px;
  }
  
  .id-number-inputs input {
    width: 140px; 
    margin-right: 5px;
    text-align: center;
  }
  
  .button-container {
    margin-top: 20px;
  }
  </style>
  