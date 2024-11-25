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

  const noticeDetail = function(noticeId){
    console.log("호출")
    return axios.get(`${REST_API_URL}/notice/${noticeId}`)
        .then((res) => {
          notice.value = res.data
          console.log(res.data)
          return notice.value
        }) 
  };

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

  const deleteNotice = function(noticeId){
    console.log("delete notice")
    axios.delete(`${REST_API_URL}/notice/remove/${noticeId}`)
    .then((res)=>{
      console.log("공지 삭제 성공:", res.data);
      getNoticeList();
    })
  }


  // 보험 조회 관련 
  const insuranceList = ref([]);
  const insurance = ref({});

  const getInsuranceList = function(){
    axios.get(`${REST_API_URL}/insurance`)
    .then((res)=>{
      insuranceList.value = res.data
    });
  }

  const detailInsurance = function(insuranceId){
    return axios.get(`${REST_API_URL}/insurance/${insuranceId}`)
    .then((res)=>{
      insurance.value = res.data.insurancePlan
      console.log(res.data.insurancePlan)
      return insurance.value
    });
  }

  const insertInsurance = function (insuranceData, file) {
    const formData = new FormData();
    formData.append("insurancePlan", new Blob([JSON.stringify(insuranceData)], { type: "application/json" }));
    formData.append("file", file);
    
    axios.post(`${REST_API_URL}/insurance/add`, formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    })
    .then((res) => {
        console.log("보험 등록 성공:", res.data);
        alert("보험 등록이 성공적으로 완료되었습니다.");
        router.push({ name: "InsuranceList" });
    })
    .catch((err) => {
        console.error("보험 등록 실패:", err.response.data);
        alert("보험 등록에 실패했습니다.");
    });
  };



  const deleteInsurance = function(insuranceId){
    console.log("delete")
    axios.delete(`${REST_API_URL}/insurance/delete/${insuranceId}`)
    .then((res)=>{
      console.log("보험 제외 성공:", res.data);
      getInsuranceList();
    })
  }
  

  const updateInsurance = function (insuranceId, insuranceData, file) {
    const formData = new FormData();
    formData.append("insurancePlan", new Blob([JSON.stringify(insuranceData)], { type: "application/json" }));
    if (file) {
        formData.append("file", file);
    }

    axios.put(`${REST_API_URL}/insurance/${insuranceId}`, formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    })
    .then((res) => {
        console.log("보험 수정 성공:", res.data);
        alert("보험 수정이 성공적으로 완료되었습니다.");
        router.push({ name: "InsuranceList" });
    })
    .catch((err) => {
        console.error("보험 수정 실패:", err.response.data);
        alert("보험 수정에 실패했습니다.");
    });
  };



  return { admin, adminLogin, userList, getUserList, searchUserList, adminLogout,
            noticeList, notice, getNoticeList, searchNoticeList, insertNotice, deleteNotice, noticeDetail,
            insuranceList, insurance, getInsuranceList, insertInsurance, deleteInsurance, updateInsurance, detailInsurance }
})