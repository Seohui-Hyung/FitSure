// 인증 관련 라우트 (로그인, 회원가입 등)

export default [
  {
    path: '/login',
    name: 'UserLogin',
    component: () => import('@/views/Auth/UserLogin.vue'),
  },
  {
    path: '/admin-login',
    name: 'AdminLogin',
    component: () => import('@/views/Auth/AdminLogin.vue'),
  },
  {
    path: '/signup',
    name: 'Signup',
    component: () => import('@/views/Auth/Signup.vue'),
  },
  {
    path: '/reset',
    name: 'PasswordReset',
    component: () => import('@/views/Auth/PasswordReset.vue'),
  }
];
