// router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import axios from 'axios'; // 세션 확인용
import AdminLogin from '@/views/Auth/AdminLogin.vue';
import AdminMain from '@/views/AdminMain.vue';
import UserList from '@/components/user/UserList.vue';
import NoticeList from '@/components/notice/NoticeList.vue';
import NoticeForm from '@/components/notice/NoticeForm.vue';
import InsuranceList from '@/components/Insurance/InsuranceList.vue';
import InsuranceForm from '@/components/Insurance/InsuranceForm.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/admin/login',
      name: 'adminLogin',
      component: AdminLogin,
    },
    {
      path: '/admin',
      name: 'AdminMain',
      component: AdminMain,
      redirect: { name: 'UserList' }, 
      children: [
        {
          path: "",
          name: "UserList",
          component: UserList
        },
        {
          path: "notice", 
          name: "NoticeList",
          component: NoticeList
        },
        {
          path: "noticeform", 
          name: "NoticeForm",
          component: NoticeForm
        },
        {
          path: "insurance", 
          name: "InsuranceList",
          component: InsuranceList
        },
        {
          path: "insurance", 
          name: "InsuranceForm",
          component: InsuranceForm
        }
      ]
    }
  ]
})

export default router;
