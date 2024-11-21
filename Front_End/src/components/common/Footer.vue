<!-- 공통 푸터 -->
<template>
    <footer class="footer">
      <div class="footer-content">
        <p>&copy; 2024 FitSure. All rights reserved.</p>
        <ul class="footer-links">
          <li><a href="/terms">Terms of Service</a></li>
          <li><a href="/privacy">Privacy Policy</a></li>
        </ul>
      </div>
    </footer>
  </template>
  
<style scoped>
  .footer {
    background-color: #043873;
    color: white;
    text-align: center;
    padding: 20px 0;
  }
  
  .footer-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
  }
  
  .footer-links {
    list-style: none;
    display: flex;
    justify-content: center;
    gap: 15px;
    padding: 0;
    margin: 0;
  }

  .footer-links li {
    margin: 0;
  }
  
  .footer-links a {
    color: white;
    text-decoration: none;
  }
</style>  