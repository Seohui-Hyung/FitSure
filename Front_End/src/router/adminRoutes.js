// 관리자 관련 라우트

export default [
  {
    path: '/admin',
    name: 'ManageUsers',
    component: () => import('@/views/Admin/ManageUsers.vue'),
    meta: { requiresAuth: true, isAdmin: true },
  },
  {
    path: '/admin-board',
    name: 'ManageBoards',
    component: () => import('@/views/Admin/ManageBoards.vue'),
    meta: { requiresAuth: true, isAdmin: true },
  },
  {
    path: '/admin-insurance',
    name: 'ManageInsuraces',
    component: () => import('@/views/Admin/ManageInsurances.vue'),
    meta: { requiresAuth: true, isAdmin: true },
  },
];
