// 보험 관련 라우트

import Traveler from '@/views/Insurance/Traveler.vue';
import Health from '@/views/Insurance/Health.vue';
import ActualExpense from '@/views/Insurance/ActualExpense.vue';

export default [
  {
    path: '/traveler',
    name: 'Traveler',
    component: Traveler,
  },
  {
    path: '/health',
    name: 'Health',
    component: Health,
  },
  {
    path: '/actualexpense',
    name: 'ActualExpense',
    component: ActualExpense,
  },
  // {
  //   path: '/insurance-signup',
  //   name: 'InsuranceSignUp',
  //   component: () => import('@/views/Insurance/InsuranceSignUp.vue')
  // }
]