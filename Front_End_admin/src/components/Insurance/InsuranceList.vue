<template>
    <div class="insurance-list-page">
        <!-- 제목 -->
        <h3 class="page-title">INSURANCE LIST</h3>
        <hr />

        <!-- 검색 바 컴포넌트 -->
        <InsuranceSearchInput />

        <!-- 테이블 -->
        <table class="insurance-table">
            <thead>
                <tr>
                    <th><input type="checkbox" v-model="selectAll" @change="toggleAllSelection" /></th>
                    <th>보험 번호</th>
                    <th>보험 종류</th>
                    <th>보험 이름</th>
                    <th>보험 가격</th>
                    <th>보험 운영 여부</th>
                </tr>
            </thead>
            <tbody>
                <tr v-if="store.insuranceList.length === 0">
                    <td colspan="6" class="no-data">검색 결과가 없습니다.</td>
                </tr>
                <tr 
                    v-for="insurance in paginatedinsurance" 
                    :key="insurance.insuranceId"
                >
                    <td>
                        <input 
                            type="checkbox" 
                            v-model="selectedInsurances" 
                            :value="insurance.insuranceId" 
                        />
                    </td>
                    <td>{{ insurance.insuranceId }}</td>
                    <td>{{ 
                        insurance.insuranceType === 1 ? "여행자 보험" : 
                        insurance.insuranceType === 2 ? "건강 보험" : 
                        insurance.insuranceType === 3 ? "실손 보험" : 
                        "알 수 없음"
                    }}</td>
                    <td>{{ insurance.insuranceName }}</td>
                    <td>{{ insurance.premium }}</td>
                    <td>{{ insurance.validity === 1 ? 'O' : 'X' }}</td>
                </tr>
            </tbody>
        </table>

        <!-- 페이징 및 버튼 -->
        <div class="table-footer">
            <!-- 페이징 -->
            <div class="pagination">
                <button 
                    :disabled="currentPage === 1" 
                    @click="changePage(currentPage - 1)"
                >
                    &lt;
                </button>
                <button 
                    v-for="page in totalPages" 
                    :key="page" 
                    :class="{ active: currentPage === page }"
                    @click="changePage(page)"
                >
                    {{ page }}
                </button>
                <button 
                    :disabled="currentPage === totalPages" 
                    @click="changePage(currentPage + 1)"
                >
                    &gt;
                </button>
            </div>

            <!-- 수정 및 삭제 버튼 -->
            <div class="action-buttons">
                <button 
                    class="edit-button"
                    @click="handleEditSelected"
                >
                    수정
                </button>
                <button 
                    class="delete-button"
                    @click="handleDeleteSelected"
                >
                    삭제
                </button>
            </div>
        </div>
    </div>
</template>


<script setup>
import { useAdminStore } from "@/store/useAdminStore";
import { computed, onMounted, ref } from "vue";
import InsuranceSearchInput from "./InsuranceSearchInput.vue";
import { useRoute, useRouter } from "vue-router";


// 스토어 초기화
const store = useAdminStore();
const router = useRouter();

onMounted(() => {
    store.getInsuranceList();
});

// 상태 관리
const currentPage = ref(1); 
const itemsPerPage = 8; 
const selectedInsurances = ref([]);
const selectAll = ref(false);

// 페이징 관련 계산
const paginatedinsurance = computed(() => {
    const startIndex = (currentPage.value - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    return store.insuranceList.slice(startIndex, endIndex);
});

const totalPages = computed(() => Math.ceil(store.insuranceList.length / itemsPerPage));

const changePage = (page) => {
    if (page > 0 && page <= totalPages.value) {
        currentPage.value = page;
    }
};

// 체크박스 관련 로직
const toggleAllSelection = () => {
    if (selectAll.value) {
        selectedInsurances.value = paginatedinsurance.value.map(item => item.insuranceId);
    } else {
        selectedInsurances.value = [];
    }
};

// 수정 및 삭제 핸들러
const handleEditSelected = () => {
    if (selectedInsurances.value.length !== 1) {
        alert("수정할 항목을 하나만 선택하세요.");
        return;
    }

    const insuranceId = selectedInsurances.value[0]; // 선택된 보험 ID
    console.log("수정할 보험 ID:", insuranceId);

    // InsuranceUpdate 라우트로 이동
    router.push({ name: "InsuranceUpdate", params: { insuranceId: insuranceId } });
};

const handleDeleteSelected = () => {
    if (selectedInsurances.value.length === 0) {
        alert("삭제할 항목을 선택하세요.");
        return;
    }
    if (confirm("선택된 항목을 운영 종료하시겠습니까?")) {
        selectedInsurances.value.forEach((insuranceId) => {
            store.deleteInsurance(insuranceId);
        });
        selectedInsurances.value = [];
        selectAll.value = false;
        
    }
};
</script>


<style scoped>
.insurance-list-page {
    padding: 20px;
    background-color: #fcfcfc;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.page-title {
    margin-bottom: 20px;
    color: #000000;
}

.insurance-table {
    width: 100%;
    border-collapse: collapse;
    background-color: #ffffff;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.insurance-table thead {
    text-align: center;
    background-color: #043873;
    color: #ffffff;
}

.insurance-table th,
.insurance-table td {
    padding: 10px;
    text-align: center;
    border-bottom: 1px solid #ddd;
}

.insurance-table tr:last-child td {
    border-bottom: none;
}

.insurance-table .no-data {
    text-align: center;
    font-style: italic;
    color: #999;
}

/* 테이블 하단 버튼 및 페이징 */
.table-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 20px;
}

.action-buttons {
    display: flex;
    gap: 10px;
}

.edit-button,
.delete-button {
    padding: 5px 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: all 0.2s;
}

.edit-button {
    background-color: #ffc107;
    color: white;
}

.delete-button {
    background-color: #dc3545;
    color: white;
}

.edit-button:hover {
    background-color: #e0a800;
}

.delete-button:hover {
    background-color: #c82333;
}

/* 페이징 */
.pagination {
    display: flex;
    gap: 5px;
}

.pagination button {
    padding: 5px 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: #fff;
    cursor: pointer;
    transition: all 0.2s;
}

.pagination button.active {
    background-color: #043873;
    color: #fff;
    font-weight: bold;
}

.pagination button:disabled {
    background-color: #f1f1f1;
    color: #ccc;
    cursor: not-allowed;
}
</style>
