// 관리자 관련 라우트

export default [
  {
    path: '/admin',
    name: 'ManageUsers',
    component: () => import('@/views/Admin/ManageUsers.vue'),
  },
  {
    path: '/admin-board',
    name: 'ManageBoards',
    component: () => import('@/views/Admin/ManageBoards.vue'),
  },
  {
    path: '/admin-insurance',
    name: 'ManageInsuraces',
    component: () => import('@/views/Admin/ManageInsurances.vue'),
  },
  {
    path: '/admin/login',
    name: 'adminLogin',
    component: () => import('@/views/Auth/AdminLogin.vue'),
  },
];
