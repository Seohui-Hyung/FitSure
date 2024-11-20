// 일반 사용자 관련 라우트

export default [
  {
    path: '/mypage',
    name: 'MyPage',
    component: () => import('@/views/MyPage/MyPage.vue'),
    meta: { requiresAuth: true },
  },
];
