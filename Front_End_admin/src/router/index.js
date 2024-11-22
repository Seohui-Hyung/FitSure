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
import InsuranceUpdate from '@/components/Insurance/InsuranceUpdate.vue';
import NoticeDetail from '@/components/notice/NoticeDetail.vue';

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
          path: "notice/:noticeId",
          name: "NoticeDetail",
          component: NoticeDetail, 
          props: true, 
        },
        {
          path: "insurance/list", 
          name: "InsuranceList",
          component: InsuranceList
        },
        {
          path: "insurance/form", 
          name: "InsuranceForm",
          component: InsuranceForm
        }, 
        {
          path: "insurance/update/:insuranceId",
          name: "InsuranceUpdate",
          component: InsuranceUpdate, // 실제 컴포넌트 이름
          props: true, // params를 props로 전달
        }
      ]
    }
  ]
})

export default router;
