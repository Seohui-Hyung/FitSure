import PayRoutine from '@/views/PayRoutine/PayRoutine.vue';

export default [
  {
    path: '/insurance/pay/:insuranceId',
    name: 'PayRoutine',
    component: PayRoutine,
  }
]