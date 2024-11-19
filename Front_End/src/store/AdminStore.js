// src/store/AdminStore.js
import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

const REST_API_URL = `http://localhost:8080/admin`;

export const useAdminStore = defineStore('admin', () => {
  const loginAdmin = ref(null);

  // 관리자 로그인 함수
  const adminLogin = async (id, password) => {
    try {
      const res = await axios.post(
        `${REST_API_URL}/login`,
        {
          adminName: id,
          adminPassword: password,
        },
        {
          // 쿠키를 포함하여 세션을 유지하도록 설정
          withCredentials: true,
        }
      );

      console.log(res);

      // 세션에 저장된 관리자 이름 사용
      const name = res.data.name; // 응답 데이터의 키 확인
      loginAdmin.value = name;

      // 로그인 성공 시 페이지 이동
      router.push({ name: 'AdminHome' });
    } catch (err) {
      console.error('Error during login:', err);

      // 로그인 실패 시 홈 화면으로 이동
      router.push({ name: 'AdminLoginForm' });
    }
  };

  return { loginAdmin, adminLogin };
});
