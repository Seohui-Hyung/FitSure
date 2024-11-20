<template>
    <div>
        <h3> User List </h3>
        <hr>

        <UserSearchInput />

        <table>
            <thead>
                <tr>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>생일</th>
                    <th>성별</th>
                    <th>탈퇴 여부</th>
                </tr>
            </thead>
            <tbody>
                <tr v-if="store.userList.length === 0">
                    <td colspan="6">검색 결과가 없습니다.</td>
                </tr>
                <tr v-for="user in store.userList" :key="user.userLoginId">
                    <td>{{ user.userLoginId }}</td>
                    <td>{{ user.username }}</td>
                    <td>{{ user.email }}</td>
                    <td>{{ formatDate(user.birthDate) }}</td>
                    <td>{{ user.gender === 'M' ? '남성' : '여성' }}</td>
                    <td>{{ user.available === 1 ? '회원' : '탈퇴 회원' }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup>
import { useAdminStore } from '@/store/useAdminStore'; 
import { onMounted } from 'vue';
import UserSearchInput from './userSearchInput.vue';

// 스토어 초기화
const store = useAdminStore();
onMounted(() => {
    store.getUserList();   
});

// 생일 포맷팅 메서드
function formatDate(dateString) {
    if (!dateString) return ''; // null 또는 undefined 처리
    return new Date(dateString).toISOString().split('T')[0];
}

</script>

<style scoped>
</style>
