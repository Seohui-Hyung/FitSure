// 게시판 관련 라우트

import BoardDetail from '@/components/board/BoardDetail.vue';

import BoardList from '@/components/board/BoardList.vue';
import BoardListView from '@/views/Board/BoardListView.vue';


export default [
  {
    path: '/board',
    name: 'BoardListView',
    component: BoardListView,
    redirect: { name: 'BoardList' }, 
    children: [
      {
        path: "",
        name: "BoardList",
        component: BoardList,
      },
      {
        path: ":noticeId",
        name: "BoardDetail",
        component: BoardDetail,
      }
    ]
  }
];
