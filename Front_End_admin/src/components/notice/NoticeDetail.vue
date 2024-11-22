<template>
    <div class="notice-detail-page">
        <!-- 제목 -->
        <h3 class="page-title">NOTICE DETAIL</h3>
        <hr />

        <!-- 공지 내용 -->
        <div class="notice-detail">
            <div class="notice-header">
                <h4 class="notice-title">{{ notice.title }}</h4>
                <div class="notice-meta">
                    <span>작성일: {{ formatDate(notice.regDate) }}</span>
                    <span>조회수: {{ notice.viewCnt }}</span>
                </div>
            </div>
            <hr />
            <div class="notice-content">
                <p v-if="notice.content">{{ notice.content }}</p>
                <p v-else class="no-data">내용이 없습니다.</p>
            </div>
        </div>

        <!-- 버튼 -->
        <div class="detail-footer">
            <button class="back-button" @click="goBack">목록으로</button>
            <button class="delete-button" @click="handleDelete">삭제</button>
        </div>
    </div>
</template>

<script setup>
import { useAdminStore } from "@/store/useAdminStore";
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const store = useAdminStore();
const route = useRoute();
const router = useRouter();

 // 공지 ID 가져오기
const notice = route.params.noticeId; // 공지 데이터

onMounted(() => {
    console.log(noticeId)
    fetchNoticeDetail();
});

// 공지 상세 정보 가져오기
const fetchNoticeDetail = (noticeId) => {
    try{
        store.noticeDetail(noticeId)
        .then((noticeId) => {
            notice = data
            console.log(notice.value)
        })
    }catch (error) {
        console.error("공지 데이터를 가져오는 중 오류 발생:", error);
    }
};

// 날짜 포맷 함수
function formatDate(dateString) {
    return new Date(dateString).toISOString().split("T")[0];
}

// 뒤로가기 버튼
const goBack = () => {
    router.push("/notice");
};

// 삭제 버튼
const handleDelete = async () => {
    if (confirm("이 공지를 삭제하시겠습니까?")) {
        await store.deleteNotice(noticeId); // 삭제 API 호출
        alert("삭제되었습니다.");
        goBack();
    }
};
</script>

<style scoped>
.notice-detail-page {
    padding: 20px;
    background-color: #fcfcfc;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.page-title {
    margin-bottom: 20px;
    color: #000000;
}

.notice-detail {
    background-color: #ffffff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.notice-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.notice-title {
    font-size: 1.5rem;
    color: #043873;
    margin: 0;
}

.notice-meta {
    font-size: 0.9rem;
    color: #777;
    display: flex;
    gap: 15px;
}

.notice-content {
    margin-top: 20px;
    line-height: 1.6;
    color: #333;
}

.notice-content .no-data {
    font-style: italic;
    color: #999;
}

/* 버튼 */
.detail-footer {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.back-button, .delete-button {
    padding: 5px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: all 0.2s;
}

.back-button {
    background-color: #6c757d;
    color: white;
}

.back-button:hover {
    background-color: #5a6268;
}

.delete-button {
    background-color: #dc3545;
    color: white;
}

.delete-button:hover {
    background-color: #c82333;
}
</style>
