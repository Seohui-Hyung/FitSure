import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/admin`

export const useAdminStore = defineStore('admin', () => {
  const admin = ref([])
  
  const adminLogin = function(adminName, adminPassword){
    axios.post(`${REST_API_URL}/login`, {
      adminName,
      adminPassword,
    })
    .then((res) => {
      admin.value = res.data
      console.log(res); 
      // 추가 동작: 페이지 이동
      router.push({ name: "ManageUsers" });
    })
    .catch((err) => {
      console.error(err.response.data); 
      router.push({ name: "adminLogin" });
    });
  }

  return { admin, adminLogin }
})