<template>
    <div class="step3-container">
        <h2>결제 정보</h2>

        <div class="amount-block">
            <p>결제 금액: <strong>{{ originalAmount.toLocaleString() }}원</strong></p>
        </div>
        <hr />
        <div class="coupon-block">
            <h3>쿠폰 적용</h3>
            <div class="coupon-row">
                <select v-model="selectedCoupon" class="coupon-select">
                    <option value="" disabled>쿠폰을 선택하세요</option>
                    <option
                        v-for="coupon in coupons"
                        :key="coupon.couponId"
                        :value="coupon.couponId"
                    >
                        24주 챌린지 달성 쿠폰 (5% 할인)
                    </option>
                </select>
                <button @click="applyCoupon" class="apply-button">쿠폰 적용</button>
            </div>
        </div>

        <!-- 최종 금액과 결제 버튼을 한 라인으로 정렬 -->
        <div class="payment-row">
            <p class="final-amount">
                최종 금액: <strong>{{ finalAmount.toLocaleString() }}원</strong>
            </p>
            <button @click="payInsurance" class="pay-button">결제하기</button>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/store/useUserStore";
import { useRoute, useRouter } from "vue-router";

const store = useUserStore();
const router = useRouter();
const route = useRoute();

const coupons = ref([]);
const insurance = ref([]);
const selectedCoupon = ref(0);
const originalAmount = ref(0);
const finalAmount = ref(0);

onMounted(async () => {
    try {
        const insuranceId = route.params.insuranceId;
        fetchCouponList();
        fetchInsurance(insuranceId);
    } catch (error) {
        console.error("Failed to fetch data:", error);
        coupons.value = [];
    }
});

const fetchCouponList = () => {
    try {
        store.getCouponList().then((response) => {
            coupons.value = response;
            selectedCoupon.value = response[0]?.couponId || 0;
        });
    } catch (error) {
        console.error("쿠폰 데이터를 가져오는 중 오류 발생:", error);
    }
};

const fetchInsurance = (insuranceId) => {
    try {
        store.detail(insuranceId).then((response) => {
            insurance.value = response;
            originalAmount.value = insurance.value.premium;
            finalAmount.value = originalAmount.value;
        });
    } catch (error) {
        console.error("보험 데이터를 가져오는 중 오류 발생:", error);
    }
};

const applyCoupon = () => {
    if (!selectedCoupon.value) {
        alert("적용할 쿠폰이 없습니다.");
        finalAmount.value = originalAmount.value;
        return;
    }

    const discount = originalAmount.value * 0.05;
    finalAmount.value = originalAmount.value - discount;
    alert(`쿠폰이 적용되었습니다! 할인 금액: ${discount.toLocaleString()}원`);
};

const payInsurance = async () => {
    try {
        const insuranceId = route.params.insuranceId;
        const couponId = selectedCoupon.value || 0;
        const amount = finalAmount.value;

        console.log("Request Data:", { insuranceId, couponId, amount });
        const redirectUrl = await store.payInsurance(insuranceId, couponId, amount);
        // window.location.href = redirectUrl || "/pay/success";
    } catch (error) {
        alert("결제에 실패했습니다. 다시 시도해주세요." + error);
    }
};
</script>

<style scoped>
.step3-container {
    max-width: 600px;
    margin: 0 auto;
    font-family: Arial, sans-serif;
    position: relative;
    padding-bottom: 60px;
}

.amount-block {
    margin-bottom: 20px;
}

.coupon-block {
    margin-bottom: 20px;
}

.coupon-row {
    display: flex;
    align-items: center;
    gap: 10px;
}

.coupon-select {
    flex: 1;
    padding: 10px;
}

.apply-button,
.pay-button {
    width: 120px; /* 동일한 너비 지정 */
    height: 40px; /* 동일한 높이 지정 */
    text-align: center;
    font-size: 14px; /* 텍스트 크기 동일 */
    background-color: #007bff; /* 쿠폰 적용 버튼 색상 */
    color: white;
    border: none;
    padding: 10px 15px;
    cursor: pointer;
    border-radius: 5px;
}

.apply-button:hover {
    background-color: #0056b3;
}

.pay-button {
    background-color: #28a745; /* 결제 버튼 색상 */
}

.pay-button:hover {
    background-color: #218838;
}

.payment-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 20px;
}

.final-amount {
    font-size: 16px;
    color: #333;
    margin: 0;
}

</style>
