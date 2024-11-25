import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API_URL = "http://localhost:8080"; // API 서버 주소

export const useUserStore = defineStore('user', () => {
  // 로그인 관련 상태
  const isAuthenticated = ref(false); // 로그인 여부
  const loggedInUser = ref({}); // 로그인된 사용자
  const authToken = ref(""); // JWT 토큰
  const couponList = ref([]);
  const insurance = ref([]);

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
  const payInsurance = async (insuranceId, couponCode = null) => {
    try {
        const token = localStorage.getItem("access-token"); // 토큰 가져오기
        if (!token) {
            throw new Error("Token is missing");
        }

        const response = await axios
          .post(`${REST_API_URL}/${insuranceId}/pay`,// 보험 ID를 경로에 포함
                { couponCode }, // 쿠폰 코드 전달
                {
                  headers: {
                    "access-token": localStorage.getItem("access-token"), // 인증 헤더
                  },
                // Axios의 리다이렉트를 따르도록 설정
                  maxRedirects: 0, // 리다이렉트 추적을 방지
                  validateStatus: (status) => status < 400 || status === 302, // 리다이렉트도 유효한 상태로 처리
                }
          );

        // 리다이렉트가 발생했으면 브라우저가 처리
        if (response.status === 302) {
            window.location.href = response.headers.location || "/pay/ready";
        }
    } catch (error) {
        console.error("Payment failed:", error);
        alert("결제에 실패했습니다. 다시 시도해주세요.");
    }
};

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
  };
});
