import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', () => {
  const verificationCode = ref(""); // 서버에서 받은 인증번호
  const verificationError = ref(""); // 인증 오류 메시지
  const isVerified = ref(false); // 인증 성공 여부

  const sendVerificationCode = async function (email) {
    console.log(email)
    try {
      const response = await axios.post("http://localhost:8080/mailSend", {
        mail: email, // 서버에서 요구하는 'mail' 키 사용
      });
      verificationCode.value = response.data.number; // 인증번호 저장
      verificationError.value = ""; // 오류 초기화
      console.log("인증번호 전송 성공:", response.data.number);
    } catch (error) {
      verificationError.value = error.response?.data?.message || "인증번호 전송 실패";
      console.error("인증번호 전송 실패:", error);
    }
  };

  const verifyCode = async function (inputCode) {
    try {
      const response = await axios.get("http://localhost:8080/mailCheck", {
        params: { userNumber: inputCode },
      });
      isVerified.value = response.data; // 인증 성공 여부 저장
      verificationError.value = response.data ? "" : "잘못된 인증번호입니다.";
      console.log("인증 결과:", response.data);
    } catch (error) {
      isVerified.value = false;
      verificationError.value = error.response?.data?.message || "인증 실패";
      console.error("인증 실패:", error);
    }
  };

  return {
    verificationCode, verificationError, isVerified, sendVerificationCode, verifyCode,
  };
});
