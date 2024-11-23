
<template>
    <div class="step3-container">
      <h2>결제 정보</h2>
  
      <!-- 결제 금액 표시 -->
      <div class="amount-block">
        <p>결제 금액: <strong>{{ totalAmount.toLocaleString() }}원</strong></p>
      </div>
  
      <!-- 쿠폰 선택 -->
      <div class="coupon-block">
        <h3>쿠폰 적용</h3>
        <select v-model="selectedCoupon" class="coupon-select">
          <option value="" disabled>쿠폰을 선택하세요</option>
          <option v-for="coupon in coupons" :key="coupon.id" :value="coupon.code">
            {{ coupon.name }} ({{ coupon.discount }}% 할인)
          </option>
        </select>
        <button @click="applyCoupon" class="apply-button">쿠폰 적용</button>
        <p class="coupon-message">{{ couponMessage }}</p>
      </div>
  
      <!-- 결제 버튼 -->
      <button @click="payInsurance" class="pay-button">결제하기</button>
    </div>
  </template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

// 상태 관리 변수
const coupons = ref([]); // 사용 가능한 쿠폰 목록
const selectedCoupon = ref(null); // 선택된 쿠폰
const couponMessage = ref(""); // 쿠폰 적용 메시지
const totalAmount = ref(100000); // 기본 결제 금액 (예: 보험료)

// 보험 ID (실제에서는 props로 전달받을 수 있음)
const insuranceId = 1; // 임의 설정

// 페이지 로드 시 쿠폰 목록 가져오기
onMounted(async () => {
  try {
    const response = await axios.get("/api/coupons"); // 쿠폰 목록 API 호출
    coupons.value = response.data;
  } catch (error) {
    console.error("Failed to fetch coupons:", error);
  }
});

// 쿠폰 적용 로직
const applyCoupon = () => {
  if (!selectedCoupon.value) {
    couponMessage.value = "적용할 쿠폰을 선택해주세요.";
    return;
  }

  if (coupons.value.some((coupon) => coupon.code === selectedCoupon.value)) {
    const discount = totalAmount.value * 0.05; // 5% 할인 적용
    totalAmount.value -= discount;
    couponMessage.value = `쿠폰이 적용되었습니다! 할인 금액: ${discount.toLocaleString()}원`;
  } else {
    couponMessage.value = "유효한 쿠폰이 아닙니다.";
  }
};

// 결제 요청 로직
const payInsurance = async () => {
  try {
    const token = localStorage.getItem("access-token"); // JWT 토큰 가져오기
    const response = await axios.post(
      `/${insuranceId}/pay`,
      {
        couponCode: selectedCoupon.value || null, // 쿠폰 코드
      },
      {
        headers: {
          "access-token": token, // 토큰을 헤더에 포함
        },
      }
    );
    window.location.href = response.data.redirectUrl || "/pay/success"; // 리다이렉트 URL
  } catch (error) {
    console.error("Payment failed:", error);
    alert("결제에 실패했습니다. 다시 시도해주세요.");
  }
};
</script>


<style scoped>
.step3-container {
  max-width: 600px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
}

.amount-block {
  margin-bottom: 20px;
}

.coupon-block {
  margin-bottom: 20px;
}

.coupon-select {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
}

.apply-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 5px;
}

.apply-button:hover {
  background-color: #0056b3;
}

.coupon-message {
  margin-top: 10px;
  color: green;
}

.pay-button {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 5px;
}

.pay-button:hover {
  background-color: #218838;
}
</style>
