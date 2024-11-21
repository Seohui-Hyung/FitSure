import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/admin`

export const useAdminStore = defineStore('admin', () => {
  const admin = ref(null);
  const userList = ref([]);

  const adminLogin = function(adminName, adminPassword){
    axios.post(`${REST_API_URL}/login`, {
      adminName,
      adminPassword,
    })
    .then((res) => {
      admin.value = res.data.name;
      console.log(admin.value)
      router.push({ name: "AdminMain" });
    })
    .catch((err) => {
      console.error(err.response.data); 
      router.push({ name: "adminLogin" });
    });
  };

  const adminLogout = function(){
    axios.post(`${REST_API_URL}/logout`, {})
    .then((res)=>{
      router.push({ name: "adminLogin" });
    })
  }
  const getUserList = function(){
    axios.get(`${REST_API_URL}/users`)
    .then((res)=>{
      userList.value = res.data
    }).catch((err) => {
      console.error(err.response.data); 
      router.push({ name: "adminLogin" });
    });
  }

  const searchUserList = function (searchCondition) {
    console.log("검색 기준:", searchCondition.key);
    console.log("검색 내용:", searchCondition.word);
    axios.get(`${REST_API_URL}/users/search`, {
      params: searchCondition
    })
      .then((res) => {
        console.log(res)
        userList.value = res.data
      })
  }


  //  notice 관련 store 

  const noticeList = ref([]);
  const notice = ref({});

  const getNoticeList = function(){
    axios.get(`${REST_API_URL}/notice`)
    .then((res)=>{
      noticeList.value = res.data
    }).catch((err) => {
      console.error(err.response.data); 
      router.push({ name: "adminLogin" });
    });
  }

  const searchNoticeList = function (searchCondition){
    axios.get(`${REST_API_URL}/notice/search`, {
      params: searchCondition
    })
      .then((res) => {
        console.log(res)
        noticeList.value = res.data
      })
  }

  const insertNotice = function (title, content) {
    const noticeData = {
      adminId: Number(admin.value), 
      title: JSON.stringify(title.title).replace(/"/g, ""), // 큰따옴표 제거
      content: content, 
    };
  
    console.log("title:", title);
  
    axios.post(`${REST_API_URL}/notice/write`, noticeData, {
      headers: {
        'Content-Type': 'application/json', 
      },
    })
      .then((res) => {
        console.log("응답 데이터:", res);
        alert("공지사항이 등록되었습니다.");
        router.push({ name: "NoticeList" });
      })
      .catch((err) => {
        console.error("에러 응답 데이터:", err.response.data); 
      });
  };


  // 보험 조회 관련 
  const insuranceList = ref([]);
  const insurance = ref({});

  const getInsuranceList = function(){
    axios.get(`${REST_API_URL}/insurance`)
    .then((res)=>{
      insuranceList.value = res.data
    });
  }



  return { admin, adminLogin, userList, getUserList, searchUserList, adminLogout,
            noticeList, notice, getNoticeList, searchNoticeList, insertNotice,
            insuranceList, insurance, getInsuranceList }
})