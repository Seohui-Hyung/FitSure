<template>
    <div class="step3-container">
        <h2>결제 정보</h2>

        <div class="amount-block">
            <p>결제 금액: <strong>{{ originalAmount.toLocaleString() }}원</strong></p>
        </div>

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

        <div v-if="finalAmount !== originalAmount" class="final-amount-block">
            <p>최종 금액: <strong>{{ finalAmount.toLocaleString() }}원</strong></p>
        </div>

        <div class="pay-button-container">
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
const originalAmount = ref(0); // 초기 금액
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
        store.getCouponList()
        .then((response) => {
            coupons.value = response;
            selectedCoupon.value = response[0].couponaId;
            console.log(response)
        })

    } catch (error) {
        console.error("공지 데이터를 가져오는 중 오류 발생:", error);
    }
};

const fetchInsurance = (insuranceId) => {
    try {
        store.detail(insuranceId)
        .then((response) => {
            insurance.value = response;
            console.log(insurance.value)
            originalAmount.value = insurance.value.premium;
            finalAmount.value = originalAmount.value;
        })

    } catch (error) {
        console.error("공지 데이터를 가져오는 중 오류 발생:", error);
    }
};


const applyCoupon = () => {
    
    if (!selectedCoupon.value) {
        alert("적용할 쿠폰이 없습니다.");
        finalAmount.value = originalAmount.value
    }

    

    if (selectedCoupon.value) {
        const discount = originalAmount.value * (5 / 100);
        finalAmount.value = originalAmount.value - discount;
        alert(`쿠폰이 적용되었습니다! 할인 금액: ${discount.toLocaleString()}원`);
        console.log(finalAmount.value)
    } 
};

const payInsurance = async () => {
    try {

        const insuranceId = route.params.insuranceId;

        console.log(insuranceId)
        const redirectUrl = await store.payInsurance(insuranceId, selectedCoupon.value, finalAmount.value);
        window.location.href = redirectUrl || "/pay/success";
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
    /* 결제 버튼 공간 확보 */
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
    /* 드롭다운과 버튼 사이 간격 */
}

.coupon-select {
    flex: 1;
    padding: 10px;
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

.final-amount-block {
    margin-top: 20px;
    font-size: 16px;
    color: #333;
}

.pay-button-container {
    position: absolute;
    bottom: 10px;
    right: 10px;
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