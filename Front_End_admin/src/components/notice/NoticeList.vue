<template>
    <div class="notice-list-page">
        <!-- 제목 -->
        <h3 class="page-title">NOTICE LIST</h3>
        <hr />

        <!-- 검색 바 컴포넌트 -->
        <NoticeSearchInput />

        <!-- 테이블 -->
        <table class="notice-table">
            <thead>
                <tr>
                    <th><input type="checkbox" v-model="selectAll" @change="toggleAllSelection" /></th>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody>
                <tr v-if="store.noticeList.length === 0">
                    <td colspan="5" class="no-data">검색 결과가 없습니다.</td>
                </tr>
                <tr v-for="notice in paginatedNotice" :key="notice.noticeId">
                    <td>
                        <input 
                            type="checkbox" 
                            v-model="selectedNotices" 
                            :value="notice.noticeId" 
                        />
                    </td>
                    <td>{{ notice.noticeId }}</td>
                    <td><a
                            class="notice-title-link"
                            @click.prevent="goToNoticeDetail(notice.noticeId)"
                        >
                            {{ notice.title }}
                        </a>
                    </td>
                    <td>{{ formatDate(notice.regDate) }}</td>
                    <td>{{ notice.viewCnt }}</td>
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

            <!-- 삭제 버튼 -->
            <div class="action-buttons">
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
import { useRoute, useRouter } from "vue-router";
import NoticeSearchInput from "./NoticeSearchInput.vue";

// 스토어 초기화
const store = useAdminStore();

onMounted(() => {
    store.getNoticeList();
});

// 상태 관리
const currentPage = ref(1); 
const itemsPerPage = 8; 
const selectedNotices = ref([]); // 선택된 공지 ID 리스트
const selectAll = ref(false); // 전체 선택 상태

const router = useRouter(); // Vue Router 인스턴스 가져오기

const goToNoticeDetail = (noticeId) => {
    router.push({ name: "NoticeDetail", params: { "noticeId": noticeId } });
};

// 페이징 관련 계산
const paginatedNotice = computed(() => {
    const startIndex = (currentPage.value - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    return store.noticeList.slice(startIndex, endIndex);
});

const totalPages = computed(() => Math.ceil(store.noticeList.length / itemsPerPage));

const changePage = (page) => {
    if (page > 0 && page <= totalPages.value) {
        currentPage.value = page;
    }
};

// 체크박스 관련 로직
const toggleAllSelection = () => {
    if (selectAll.value) {
        selectedNotices.value = paginatedNotice.value.map(item => item.noticeId);
    } else {
        selectedNotices.value = [];
    }
};

// 날짜 포맷 함수
function formatDate(dateString) {
    return new Date(dateString).toISOString().split("T")[0];
}

// 삭제 로직
const handleDeleteSelected = () => {
    if (selectedNotices.value.length === 0) {
        alert("삭제할 항목을 선택하세요.");
        return;
    }
    if (confirm("선택된 항목을 삭제하시겠습니까?")) {
        selectedNotices.value.forEach((noticeId) => {
            store.deleteNotice(noticeId) // 삭제 API 호출
        });
        // 선택 초기화
        selectedNotices.value = [];
        selectAll.value = false;
    }
};
</script>

<style scoped>
.notice-list-page {
    padding: 20px;
    background-color: #fcfcfc;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.page-title {
    margin-bottom: 20px;
    color: #000000;
}

.notice-table {
    width: 100%;
    border-collapse: collapse;
    background-color: #ffffff;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.notice-table thead {
    text-align: center;
    background-color: #043873;
    color: #ffffff;
}

.notice-table th,
.notice-table td {
    padding: 10px;
    text-align: center;
    border-bottom: 1px solid #ddd;
}

.notice-table tr:last-child td {
    border-bottom: none;
}

.notice-table .no-data {
    text-align: center;
    font-style: italic;
    color: #999;
}

/* 페이징 및 버튼 */
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

.delete-button {
    padding: 5px 10px;
    border: none;
    border-radius: 5px;
    background-color: #dc3545;
    color: white;
    cursor: pointer;
    transition: all 0.2s;
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
