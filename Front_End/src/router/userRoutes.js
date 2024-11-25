// 일반 사용자 관련 라우트
import MyPage from '@/views/MyPage/MyPage.vue';

export default [
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage,
    // meta: { requiresAuth: true },
  },
];
