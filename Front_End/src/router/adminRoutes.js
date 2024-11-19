// 관리자 관련 라우트

export default [
  {
    path: '/admin',
    name: 'ManageBoards',
    component: () => import('@/views/Admin/ManageBoards.vue'),
    meta: { requiresAuth: true, isAdmin: true },
  },
];
