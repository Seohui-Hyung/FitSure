// 인증 관련 라우트 (로그인, 회원가입 등)
import UserLogin from '@/views/Auth/UserLogin.vue';
import Find_IDPW from '@/views/Auth/Find_IDPW.vue';
import InfoChange from '@/views/Auth/InfoChange.vue';

export default [
  {
    path: '/login',
    name: 'UserLogin',
    component: UserLogin,
  },
  {
    path: '/signup',
    name: 'Signup',
    component: () => import('@/views/Auth/Signup.vue'),
  },
  {
    path: '/findIdPw',
    name: 'Find_IDPW',
    component: Find_IDPW,
  },
  {
    path: '/auth/info-change',
    name: 'InfoChange',
    component: InfoChange,
  }
];
