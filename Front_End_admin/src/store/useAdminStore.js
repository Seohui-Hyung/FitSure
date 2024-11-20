import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/admin`

export const useAdminStore = defineStore('admin', () => {
  const admin = ref([]);
  const userList = ref([]);

  const adminLogin = function(adminName, adminPassword){
    axios.post(`${REST_API_URL}/login`, {
      adminName,
      adminPassword,
    })
    .then((res) => {
      admin.value = res.data
      console.log(res); 
      // 추가 동작: 페이지 이동
      router.push({ name: "MainAdmin" });
    })
    .catch((err) => {
      console.error(err.response.data); 
      router.push({ name: "adminLogin" });
    });
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

  return { admin, adminLogin, userList, getUserList, searchUserList }
})