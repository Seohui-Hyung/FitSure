import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router'

const REST_API_URL = "http://localhost:8080"; // API 서버 주소

export const useUserStore = defineStore('user', () => {
  // 로그인 관련 상태
  const isAuthenticated = ref(false); // 로그인 여부
  const loggedInUser = ref({}); // 로그인된 사용자
  const authToken = ref(""); // JWT 토큰
  const couponList = ref([]);
  const insurance = ref([]);
  const insuranceList = ref([])

  // 로그인 메서드
  async function login(userLoginId, password) {
    try {
      const response = await axios.post(`${REST_API_URL}/user/token/login`, {
        userLoginId,
        password,
      });
  
      // JWT 토큰 저장
      const token = response.data["access-token"];
      authToken.value = token;
  
      // 세션에 토큰 저장
      localStorage.setItem("access-token", token);
      
      // 사용자 정보 업데이트
      isAuthenticated.value = true;
  
      return true;
    } catch (error) {
      console.error("로그인 실패:", error);
      return false;
    }
  }

  // // 로그아웃 메서드
  // function logout() {
  //   isAuthenticated.value = false;
  //   loggedInUser.value = "";
  //   authToken.value = "";
  //   localStorage.removeItem("access-token");
  // }

  // 토큰 검증 (페이지 새로고침 시 로그인 상태 유지)
  function checkAuth() {
      // 서버에 사용자 정보를 요청하여 상태를 업데이트
      return axios
        .get(`${REST_API_URL}/user/token/auth/me`, {
          headers: { "access-token": localStorage.getItem("access-token") },
        })
        .then((response) => {
          return loggedInUser.value = response.data; 
        })
        .catch((error) => {
          console.error("인증 실패:", error);
        });
  }

  const updateUser = (username, password, email) => {
    const data = {
      username,
      password,
      email
    };
  
  
    axios.patch(`${REST_API_URL}/user/token/update`, data, {
      headers: {
        "Content-Type": "application/json",
        "access-token": localStorage.getItem("access-token")
      }
    })
    .then((res) => {
      console.log(res);
      router.push({ name: "MyPage" });
    }).catch((error) => {
      console.error(error);
    });
  };

  const deleteUser = async () => {
    axios.put(`${REST_API_URL}/user/token/delete`, {}, {
      headers: { "access-token": localStorage.getItem("access-token") }
    })
    .then((res) => {
      console.log(res);
      router.push({ name: "Main" });
    })
    .catch((error) => {
      console.error("Error deleting user:", error);
    });
    
  };

  // 쿠폰 리스트 가져오기
  const getCouponList = async () => {
    try {
      return axios
        .get(`${REST_API_URL}/user/goal/getCoupon/unused`, {
          headers: { "access-token": localStorage.getItem("access-token") },
        }).then((response)=>{
          return couponList.value = response.data; // 쿠폰 리스트 저장
        })
    } catch (error) {
      console.error("Failed to fetch coupons:", error); // 오류 처리
      couponList.value = [];
      return [];
    }
  };




  // 보험 상세 정보 가져오기
  const detail = async (insuranceId) => {
    try {
      return axios
        .get(`${REST_API_URL}/insurance/${insuranceId}`)
        .then((response) => {
          return insurance.value = response.data;
        })
    } catch (error) {
      console.error("Failed to fetch insurance details:", error); // 오류 처리
      insurance.value = {}; // 초기화
    }
  };

  // 결제 요청 메서드
const payInsurance = async (insuranceId, couponCode, finalAmount) => {
  try {
    const response = await axios.post(
      `${REST_API_URL}/insurance/${insuranceId}/pay`,
      {
        couponCode,
        finalAmount,
      },
      {
        headers: {
          "access-token": localStorage.getItem("access-token"), // 인증 헤더
        },
        withCredentials: true,
      }
    );

    // 서버 응답 처리
    const responseData = response.data; // 서버에서 전달된 응답 데이터

    if (responseData && responseData.next_redirect_pc_url) {
      // 리다이렉트 URL이 응답에 포함되어 있다면 리다이렉트 실행
      location.href = responseData.next_redirect_pc_url;
    } else {
      console.error("리다이렉트 URL이 응답에 포함되어 있지 않습니다.");
    }
  } catch (error) {
    console.error("결제 요청 중 오류가 발생했습니다:", error);
  }
};

  // 공지 사항 관련 
  const noticeList = ref([]);
  const notice = ref({});

  const getNoticeList = function(){
    axios.get(`${REST_API_URL}/notice`)
    .then((res)=>{
      noticeList.value = res.data
    }).catch((err) => {
      console.error(err.response.data); 
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


  const createGoal = function(){
    axios.post(`${REST_API_URL}/user/goal/create`,{},{
      headers: {
        "access-token": localStorage.getItem("access-token")
      }
    }).then((res) => {
      return "ok";
    })
  }

  const goalList = ref({})

  const getGoalList = function(){
    return axios.get(`${REST_API_URL}/user/goal`, {
      headers: {
        "access-token": localStorage.getItem("access-token")
      }
    }).then((res)=>{
      goalList.value = res.data
      console.log(goalList.value)
      return goalList.value
    })
  }


  const findmySubscribe = function(){
    return axios.get(`${REST_API_URL}/insurance/ongoing`, {
      headers: {
        "access-token": localStorage.getItem("access-token")
      }
    }).then((res)=>{
      insuranceList.value = res.data
      console.log(goalList.value)
      return insuranceList.value
    })
  }
  

  // // 입력 초기화
  // const resetForm = () => {
  //   username.value = "";
  //   email.value = "";
  //   userLoginId.value = "";
  //   foundId.value = null;
  //   foundPassword.value = null;
  //   searchAttempted.value = false;
  //   verificationCode.value = "";
  //   verificationError.value = "";
  //   isVerified.value = false;
  // };

  return {
    // // 로그인 토큰
    isAuthenticated,
    loggedInUser,
    authToken,
    login,
    // logout, // 주석 유지
    checkAuth,
    // // 메서드
    getCouponList,
    detail,
    payInsurance,
    // resetForm, // 주석 유지

    // 공지사항 
    getNoticeList, noticeList, notice, noticeDetail, searchNoticeList,

    updateUser, deleteUser, createGoal, getGoalList, goalList,
    findmySubscribe, insuranceList
  };
});
