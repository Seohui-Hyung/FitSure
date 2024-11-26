<template>
    <div class="notice-detail-page">
        <!-- 제목 -->
        <h3 class="page-title">NOTICE DETAIL</h3>
        <hr />

        <!-- 공지 내용 -->
         <div class="notice-detail">
            <div class="notice-header">
                <h4 class="notice-title">{{ store.notice.title }}</h4>
                <div class="notice-meta">
                    <!-- <span>작성일: {{ formattedRegDate(store.notice.regDate) }}</span> -->
                    <span>조회수: {{ store.notice.viewCnt }}</span>
                </div>
            </div>
            <hr />
            <div class="notice-content">
                <div v-if="store.notice.content" v-html="store.notice.content"></div>
                <p v-else class="no-data">내용이 없습니다.</p>
            </div>
        </div> 

        <!-- 버튼 -->
        <div class="detail-footer">
            <button class="back-button" @click="goBack">목록으로</button>
        </div>
    </div>
</template>

<script setup>
import { useUserStore } from "@/store/useUserStore";
import { reactive, onMounted, ref, computed } from "vue";
import { useRoute, useRouter } from "vue-router";

const store = useUserStore();
const route = useRoute();
const router = useRouter();

 // 공지 ID 가져오기


onMounted(() => {
    const noticeId = route.params.noticeId; // 공지 데이터
    console.log(noticeId)
    fetchNoticeDetail(noticeId);
});

// 공지 상세 정보 가져오기
const fetchNoticeDetail = (noticeId) => {
    try{
        store.noticeDetail(noticeId)
        .then((notice)=>{
            notice.title = notice.title;
            notice.content = notice.content
            notice.regDate = notice.regDate
            notice.viewCnt = notice.viewCnt
        })
        
    } catch (error) {
        console.error("공지 데이터를 가져오는 중 오류 발생:", error);
    }
};

const formattedRegDate = computed(() => {
    const regDate = store.notice.regDate; 
    return new Date(regDate).toISOString().split("T")[0]; // 날짜 포맷팅
});

// 뒤로가기 버튼
const goBack = () => {
    router.push("/board");
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
