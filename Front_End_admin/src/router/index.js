// router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import axios from 'axios'; // 세션 확인용

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/admin/login',
      name: 'adminLogin',
      component: () => import('@/views/Auth/AdminLogin.vue'),
    },
    {
      path: '/admin',
      name: 'MainAdmin',
      component: () => import('@/views/AdminMain.vue'),
      children: [
        {
          path: "",
          name: "UserList",
          component: () => import('@/components/user/UserList.vue')
        }
      ]
    }
  ]

})



export default router;