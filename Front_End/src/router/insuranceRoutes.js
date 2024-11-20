// 보험 관련 라우트

export default [
  {
    path: '/insurance-detail',
    name: 'InsuranceDetail',
    component: () => import('@/views/Insurance/InsuranceDetail.vue'),
  },
  {
    path: '/insurance',
    name: 'InsuranceList',
    component: () => import('@/views/Insurance/InsuranceList.vue'),
  },
  {
    path: '/insurance-signup',
    name: 'InsuranceSignUp',
    component: () => import('@/views/Insurance/InsuranceSignUp.vue')
  }
]