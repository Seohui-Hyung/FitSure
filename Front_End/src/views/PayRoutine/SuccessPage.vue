<template>
    <div class="checkmark">
      <svg class="checkmark__check" height="36" viewBox="0 0 48 36" width="48" xmlns="http://www.w3.org/2000/svg">
        <path
          d="M47.248 3.9L43.906.667a2.428 2.428 0 0 0-3.344 0l-23.63 23.09-9.554-9.338a2.432 2.432 0 0 0-3.345 0L.692 17.654a2.236 2.236 0 0 0 .002 3.233l14.567 14.175c.926.894 2.42.894 3.342.01L47.248 7.128c.922-.89.922-2.34 0-3.23"
          fill="#fff" />
      </svg>
      <svg class="checkmark__back" height="115" viewBox="0 0 120 115" width="120" xmlns="http://www.w3.org/2000/svg">
        <path
          d="M107.332 72.938c-1.798 5.557 4.564 15.334 1.21 19.96-3.387 4.674-14.646 1.605-19.298 5.003-4.61 3.368-5.163 15.074-10.695 16.878-5.344 1.743-12.628-7.35-18.545-7.35-5.922 0-13.206 9.088-18.543 7.345-5.538-1.804-6.09-13.515-10.696-16.877-4.657-3.398-15.91-.334-19.297-5.002-3.356-4.627 3.006-14.404 1.208-19.962C10.93 67.576 0 63.442 0 57.5c0-5.943 10.93-10.076 12.668-15.438 1.798-5.557-4.564-15.334-1.21-19.96 3.387-4.674 14.646-1.605 19.298-5.003C35.366 13.73 35.92 2.025 41.45.22c5.344-1.743 12.628 7.35 18.545 7.35 5.922 0 13.206-9.088 18.543-7.345 5.538 1.804 6.09 13.515 10.696 16.877 4.657 3.398 15.91.334 19.297 5.002 3.356 4.627-3.006 14.404-1.208 19.962C109.07 47.424 120 51.562 120 57.5c0 5.943-10.93 10.076-12.668 15.438z"
          fill="#218838" />
      </svg>
      <p class="message">결제를 완료하였습니다</p>
      <p class="sub-message">내역은 마이 페이지에서 확인하세요</p>
      <RouterLink to="/mypage" class="go-to-mypage">마이페이지로 이동</RouterLink>
    </div>
  </template>
  
  <script setup>
  // 추가적인 JavaScript 로직이 필요하면 여기에 작성하세요.
  </script>
  
  <style scoped>
  *,
  *::before,
  *::after {
    box-sizing: border-box;
  }
  
  body {
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #fff; /* 흰색 배경 */
  }
  .checkmark {
    position: relative;
    padding: 30px;
    margin-top: 150px;
    animation: checkmark 5.6s cubic-bezier(0.42, 0, 0.275, 1.155) both;
    text-align: center;
  }
  
  .message {
    font-size: 1.5rem;
    font-weight: bold;
    margin-top: 1.5rem;
    color: #043873; /* 파란색 텍스트 */
  }
  
  .sub-message {
    font-size: 1rem;
    color: gray;
    margin-top: 0.5rem;
  }
  
  .checkmark__check {
    position: absolute;
    top: 25%;
    left: 50%;
    z-index: 10;
    transform: translate3d(-50%, -50%, 0);
    fill: #c0c0c0; /* 체크 표식 */
  }
  
  .checkmark__back {
    animation: rotate 35s linear both infinite;
    fill: #218838; /* 돌아가는 배경 파란색 */
  }
  
  @keyframes rotate {
    0% {
      transform: rotate(0deg);
    }
  
    100% {
      transform: rotate(360deg);
    }
  }
  
  @keyframes grow {
    0%,
    100% {
      transform: scale(0);
    }
  
    50% {
      transform: scale(1);
    }
  }
  
  /* 마지막 사라지는 효과 제거 */
  @keyframes checkmark {
    0% {
      opacity: 1;
      transform: scale(0);
    }
  
    10%, 50%, 90% {
      opacity: 1;
      transform: scale(1);
    }
  }
  
  .go-to-mypage {
    display: inline-block;
    margin-top: 1rem;
    padding: 0.75rem 1.5rem;
    background-color: #054c9c; /* 노란색 배경 */
    color: #fff; /* 흰색 텍스트 */
    text-decoration: none;
    border-radius: 5px;
    font-size: 1rem;
    text-align: center;
    font-weight: bold;
    transition: background-color 0.3s ease;
  }
  
  .go-to-mypage:hover {
    background-color: #022a58; /* hover 상태에서 어두운 노란색 */
  }
  </style>
  