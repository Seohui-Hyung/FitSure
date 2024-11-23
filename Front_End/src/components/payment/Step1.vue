<template>
  <div>
    <div>
      <h2>피보험자/계약자 정보를 입력해 주세요</h2>
      <p>피보험자와 계약자가 동일해야 합니다.</p>
      <div>
        <label>이름</label>
        <input type="text" placeholder="이름을 입력하세요" />
      </div>
      <div>
        <label>주민등록번호</label>
        <input type="text" placeholder="앞자리 입력" maxlength="6" />
        -
        <input type="password" placeholder="뒷자리 입력" maxlength="7" />
      </div>
      <div>
        <label>이메일</label>
        <input type="email" placeholder="이메일을 입력하세요" />
      </div>
    </div>

    <div>
      <h3>필수 확인 사항</h3>
      <button @click="showModal = true">확인</button>
    </div>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <h2>고객정보 관리 및 가입설계를 위해 동의가 필요합니다.</h2>
        <div>
          <input type="checkbox" v-model="allChecked" @change="toggleAll" />
          <label>모두 동의하기</label>
        </div>
        <div v-for="item in agreements" :key="item.id">
          <input
            type="checkbox"
            v-model="item.checked"
            @change="updateAllChecked"
          />
          <label>{{ item.label }}</label>
        </div>
        <div>
          <button @click="closeModal">취소</button>
          <button @click="closeModal">확인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

// 모달 표시 상태 관리
const showModal = ref(false);

// 체크박스 데이터 관리
const agreements = ref([
  { id: 1, label: "개인(신용)정보의 수집·이용에 관한 사항", checked: false },
  { id: 2, label: "개인(신용)정보의 제공에 관한 사항", checked: false },
  { id: 3, label: "개인(신용)정보의 조회에 관한 사항", checked: false },
  { id: 4, label: "소비자 권익 보호에 관한 사항", checked: false },
]);

// 모두 동의 상태 관리
const allChecked = ref(false);

// 모두 동의 버튼 처리
const toggleAll = () => {
  allChecked.value = !allChecked.value;
  agreements.value.forEach((item) => {
    item.checked = allChecked.value;
  });
};

// 개별 체크박스 변경 처리
const updateAllChecked = () => {
  allChecked.value = agreements.value.every((item) => item.checked);
};

// 팝업 닫기
const closeModal = () => {
  showModal.value = false;
};
</script>



<style>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 500px;
  width: 100%;
}

.modal-content h2 {
  margin-bottom: 20px;
}

.modal-content div {
  margin-bottom: 10px;
}
</style>
