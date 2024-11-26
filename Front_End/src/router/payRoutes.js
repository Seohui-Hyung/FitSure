import PayRoutine from '@/views/PayRoutine/PayRoutine.vue';
import SuccessPage from '@/views/PayRoutine/SuccessPage.vue';

export default [
  {
    path: '/insurance/pay/:insuranceId',
    name: 'PayRoutine',
    component: PayRoutine,
  },
  {
    path: '/insurance/pay/success',
    name: 'SuccessPage',
    component: SuccessPage,
  }
]