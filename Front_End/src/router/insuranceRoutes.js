// 보험 관련 라우트

export default [
  {
    path: '/insurance-signup',
    name: 'InsuranceSignUp',
    component: () => import('@/views/Insurance/InsuranceSignUp.vue')
  }
]