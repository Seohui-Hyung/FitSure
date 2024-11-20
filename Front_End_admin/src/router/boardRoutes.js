// 게시판 관련 라우트

export default [
  {
    path: '/board-detail',
    name: 'BoardDetailView',
    component: () => import('@/views/Board/BoardDetailView.vue'),
  },
  {
    path: '/board',
    name: 'BoardListView',
    component: () => import('@/views/Board/BoardListView.vue'),
  }
];
