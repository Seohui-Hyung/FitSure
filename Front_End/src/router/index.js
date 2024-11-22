// router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import authRoutes from './authRoutes';
import userRoutes from './userRoutes';
import axios from 'axios'; // 세션 확인용
import Main from '@/views/Main.vue';
import insuranceRoutes from './insuranceRoutes';

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  ...authRoutes,
  // ...boardRoutes,
  ...userRoutes,
  ...insuranceRoutes,
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 세션 및 토큰 기반 인증 확인
router.beforeEach(async (to, from, next) => {
  console.log('Navigating to:', to.name);
  console.log('Requires Auth:', to.meta.requiresAuth);
  console.log('User Token:', localStorage.getItem('access-token'));
  
  const userToken = localStorage.getItem('access-token'); // 사용자 토큰
  const isAdminSessionValid = await checkAdminSession(); // 관리자 세션 확인

  if (to.meta.requiresAuth && !userToken) {
    // 사용자가 인증이 필요한 페이지에 접근하려는 경우
    alert('로그인이 필요합니다.');
    next({ name: 'UserLogin' });
  } else if (to.meta.isAdmin && !isAdminSessionValid) {
    // 관리자가 인증이 필요한 페이지에 접근하려는 경우
    alert('관리자 권한이 필요합니다.');
    next({ name: 'AdminLogin' });
  } else {
    next();
  }
});

// 관리자 세션 확인 함수
async function checkAdminSession() {
  try {
    const response = await axios.get('/api/admin/session-check'); // 서버의 세션 확인 API
    return response.status === 200; // 세션이 유효하면 true
  } catch (error) {
    return false; // 세션이 유효하지 않으면 false
  }
}

export default router;