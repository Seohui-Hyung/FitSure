<template>
    <div class="search-bar">
        <!-- 드롭다운 메뉴 -->
        <div class="dropdown-wrapper">
            <select class="dropdown-select" v-model="searchCondition.key">
                <option value="검색 조건" disabled>검색 조건</option>
                <option value="title">제목</option>
                <option value="content">내용</option>
            </select>
        </div>

        <!-- 검색 입력 -->
        <div class="input-wrapper">
            <input
                type="text"
                class="search-input"
                placeholder="검색어를 입력하세요"
                v-model="searchCondition.word"
            />
            <!-- 검색 버튼 -->
            <button class="search-btn" @click="searchNoticeList">
                <a>🔍</a>
            </button>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useAdminStore } from "@/store/useAdminStore";

// 검색 조건 상태
const searchCondition = ref({
    key: "검색 조건",
    word: "",
});

const store = useAdminStore();

// 검색 함수
const searchNoticeList = () => {
    if (!searchCondition.value.key || searchCondition.value.key === "검색 조건") {
        console.error("검색 기준을 선택하세요.");
        return;
    }
    if (!searchCondition.value.word.trim()) {
        console.error("검색 내용을 입력하세요.");
        return;
    }

    store.searchNoticeList(searchCondition.value);
};
</script>

<style scoped>
.search-bar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 15px; 
    padding: 10px;
    margin-bottom: 20px;
    background-color: #f8f9fa;
    border-radius: 8px; 
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.24);
}

/* 검색 입력 및 버튼 래퍼 */
.input-wrapper {
    display: flex;
    align-items: center;
    flex-grow: 1;
    position: relative;
}

.search-input {
    flex-grow: 1;
    padding: 10px 15px; 
    font-size: 16px;
    border: 1px solid #ced4da; 
    border-radius: 8px 0 0 8px; 
    outline: none;
    transition: border-color 0.2s;
}

.search-input:focus {
    border-color: #495057; 
}

.search-btn {
    width: 50px;
    height: 46px;
    background-color: #043873bb; 
    color: #ffffff; 
    border: none;
    border-radius: 0 8px 8px 0; 
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: background-color 0.2s, transform 0.2s;
}

.search-btn:hover {
    background-color: #03428a; 
    transform: scale(1.05); 
}

.dropdown-wrapper {
    flex-shrink: 0; 
}

.dropdown-select {
    padding: 10px 15px;
    font-size: 16px;
    border: 1px solid #ced4da;
    border-radius: 8px;
    background-color: #fff;
    outline: none;
    cursor: pointer;
    transition: border-color 0.2s;
}

.dropdown-select:focus {
    border-color: #495057;
}

.dropdown-select option {
    font-size: 14px;
}
</style>
