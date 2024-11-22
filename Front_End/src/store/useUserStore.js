import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router'

const REST_API_URL = "http://localhost:8080"; // API 서버 주소

export const useUserStore = defineStore('user', () => {
  // 로그인 관련 상태
  const isAuthenticated = ref(false); // 로그인 여부
  const loggedInUser = ref(""); // 로그인된 사용자 이름
  const authToken = ref(""); // JWT 토큰

  //상태
  const username = ref("");
  const email = ref("");
  const userLoginId = ref("");
  const foundId = ref(null);
  const foundPassword = ref(null);
  const searchAttempted = ref(false);

  // 인증 관련 상태
  const verificationCode = ref(""); // 서버에서 받은 인증번호
  const verificationError = ref(""); // 인증 오류 메시지
  const isVerified = ref(false); // 인증 성공 여부

  // 로그인 관련 로직
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
      sessionStorage.setItem("access-token", token);
      
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

  // // 토큰 검증 (페이지 새로고침 시 로그인 상태 유지)
  // function checkAuth() {
  //   const token = localStorage.getItem("access-token");
  //   if (token) {
  //     authToken.value = token;
  //     isAuthenticated.value = true;

  //     // 서버에 사용자 정보를 요청하여 상태를 업데이트
  //     axios
  //       .get(`${API_URL}/auth/me`, {
  //         headers: {
  //           Authorization: `Bearer ${token}`,
  //         },
  //       })
  //       .then((response) => {
  //         loggedInUser.value = response.data.username; // 서버에서 사용자 이름 반환
  //       })
  //       .catch((error) => {
  //         console.error("인증 실패:", error);
  //         logout();
  //       });
  //   }
  // }

  // // 아이디 찾기 로직  
  // const findId = () => {
  //   if (!username.value || !email.value) {
  //     console.error("모든 필드를 입력해야 합니다.");
  //     return;
  //   }

  //   searchAttempted.value = true;
  //   axios
  //     .post(`${REST_API_URL}/user/token/findId`, {
  //       username: username.value,
  //       email: email.value,
  //     })
  //     .then((response) => {
  //       foundId.value = response.data.id || null; // 서버에서 반환된 아이디 저장
  //     })
  //     .catch((error) => {
  //       console.error("아이디 찾기 실패:", error);
  //       foundId.value = null;
  //     });
  // };  

  // // 비밀번호 찾기 로직
  // const findPassword = () => {
  //   if (!userLoginId.value || !email.value) {
  //     console.error("모든 필드를 입력해야 합니다.");
  //     return;
  //   }

  //   searchAttempted.value = true;
  //   axios
  //     .post(`${REST_API_URL}/user/token/resetpassword`, {
  //       userLoginId: userLoginId.value,
  //       email: email.value,
  //     })
  //     .then((response) => {
  //       foundPassword.value = response.data.message || null; // 서버에서 반환된 메시지 저장
  //     })
  //     .catch((error) => {
  //       console.error("비밀번호 찾기 실패:", error);
  //       foundPassword.value = null;
  //     });
  // };

  // 인증번호 전송
  const sendVerificationCode = (email) => {
    console.log(email);
    axios
      .post(`${REST_API_URL}/mailSend`, {
        mail: email, // 서버에서 요구하는 'mail' 키 사용
      })
      .then((response) => {
        verificationCode.value = response.data.number; // 인증번호 저장
        verificationError.value = ""; // 오류 초기화
        console.log("인증번호 전송 성공:", response.data.number);
      })
      .catch((error) => {
        verificationError.value = error.response?.data?.message || "인증번호 전송 실패";
        console.error("인증번호 전송 실패:", error);
      });
  };

  // 인증번호 확인
  const verifyCode = (inputCode) => {
    axios
      .get(`${REST_API_URL}/mailCheck`, {
        params: { userNumber: inputCode },
      })
      .then((response) => {
        isVerified.value = response.data; // 인증 성공 여부 저장
        verificationError.value = response.data ? "" : "잘못된 인증번호입니다.";
        console.log("인증 결과:", response.data);
      })
      .catch((error) => {
        isVerified.value = false;
        verificationError.value = error.response?.data?.message || "인증 실패";
        console.error("인증 실패:", error);
      });
  };

  //  // 입력 초기화
  //  const resetForm = () => {
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
    // logout,
    // checkAuth,
    // // 상태
    // username,
    // email,
    // userLoginId,
    // foundId,
    // foundPassword,
    // searchAttempted,
    verificationCode,
    verificationError,
    isVerified,
    // 메서드
    // findId,
    // findPassword,
    sendVerificationCode,
    verifyCode,
    // resetForm,
  };
});
