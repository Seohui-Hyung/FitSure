<template>
    <div class="login-container">
      <h1>Admin Login</h1>
      <form @submit.prevent="submitLogin">
        <div class="form-group">
          <label for="username">Username:</label>
          <input
            id="username"
            v-model="username"
            type="text"
            class="form-control"
            placeholder="Enter your username"
            required
          />
        </div>
  
        <div class="form-group">
          <label for="password">Password:</label>
          <input
            id="password"
            v-model="password"
            type="password"
            class="form-control"
            placeholder="Enter your password"
            required
          />
        </div>
  
        <button type="submit" class="btn btn-primary mt-3">Login</button>
      </form>
    </div>
  </template>
  
  <script>
  import { ref } from "vue";
  import { useAdminStore } from "@/store/AdminStore.js";
  
  export default {
    setup() {
      const adminStore = useAdminStore();
      const username = ref(""); // reactive 변수
      const password = ref("");

      const submitLogin = async () => {
        try {
          await adminStore.adminLogin(username.value, password.value);
        } catch (err) {
          console.error("Error during login:", err);
        }
      };
  
      return {
        username,
        password,
        submitLogin,
      };
    },
  };
  </script>
  
  <style scoped>
  .login-container {
    max-width: 400px;
    margin: 50px auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: #fff;
  }
  </style>
  