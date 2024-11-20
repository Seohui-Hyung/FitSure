<template>
    <div class="search-bar">
        <!-- 드롭다운 메뉴 -->
        <div class="dropdown">
            <select class="dropdown-select" v-model="searchCondition.key">
                <option value="none">검색 기준</option>
                <option value="name">이름</option>
                <option value="email">이메일</option>
                <option value="birthDate">생년월일</option>
                <option value="gender">성별</option>
            </select>
        </div>
        <!-- 검색 입력 -->
        <input
            type="text"
            class="search-input"
            placeholder="검색 내용을 입력하세요"
            v-model="searchCondition.word"
        />
        <!-- 검색 버튼 -->
        <button class="search-btn" @click="searchUserList">🔍</button>
    </div>
</template>



<script setup>
import { ref } from "vue";
import { useAdminStore } from "@/store/useAdminStore";

// 검색 조건 상태
const searchCondition = ref({
    key: "none",
    word: "",
});

const store = useAdminStore();

// 검색 함수
const searchUserList = () => {
    if (!searchCondition.value.key || searchCondition.value.key === "none") {
        console.error("검색 기준을 선택하세요.");
        return;
    }
    if (!searchCondition.value.word.trim()) {
        console.error("검색 내용을 입력하세요.");
        return;
    }

    store.searchUserList(searchCondition.value);
};
</script>



<style scoped>
/* 전체 검색바 스타일 */
.search-bar {
    display: flex;
    align-items: center;
    background-color: #f9f9f9;
    border: 1px solid #ccc;
    border-radius: 25px;
    padding: 5px 15px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    width: 500px;
    gap: 10px;
}

/* 드롭다운 스타일 */
.dropdown-select {
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 5px 10px;
    font-size: 14px;
    outline: none;
    cursor: pointer;
    flex: 1;
}

.dropdown-select:focus {
    border-color: #007bff;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

/* 검색 입력 스타일 */
.search-input {
    flex: 2;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 5px 10px;
    font-size: 14px;
    outline: none;
}

.search-input:focus {
    border-color: #007bff;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

/* 검색 버튼 스타일 */
.search-btn {
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    padding: 5px 15px;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.search-btn:hover {
    background-color: #0056b3;
}

.search-btn:active {
    background-color: #003d80;
}
</style>
