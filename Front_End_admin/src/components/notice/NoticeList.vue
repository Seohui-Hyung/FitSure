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
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody>
                <tr v-if="store.noticeList.length === 0">
                    <td colspan="4" class="no-data">검색 결과가 없습니다.</td>
                </tr>
                <tr v-for="notice in paginatedNotice" :key="notice.noticeId">
                    <td>{{ notice.noticeId }}</td>
                    <td>{{ notice.title }}</td>
                    <td>{{ formatDate(notice.regDate) }}</td>
                    <td>{{ notice.viewCnt }}</td>
                </tr>
            </tbody>
        </table>

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
    </div>
</template>


<script setup>
import { useAdminStore } from "@/store/useAdminStore";
import { computed, onMounted, ref } from "vue";
import NoticeSearchInput from "./NoticeSearchInput.vue";

// 스토어 초기화
const store = useAdminStore();

onMounted(() => {
    store.getNoticeList();
});

const currentPage = ref(1); 
const itemsPerPage = 8; 

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

function formatDate(dateString) {
    return new Date(dateString).toISOString().split("T")[0];
}
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

.notice-table th {
    padding: 10px;
    text-align: center;
    border-bottom: 1px solid #ddd;
}

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

/* 페이징 */
.pagination {
    margin-top: 20px;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
}

.pagination button {
    margin: 0 5px;
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

