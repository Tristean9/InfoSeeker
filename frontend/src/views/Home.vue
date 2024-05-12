<template>
  <div class="home">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
      <div class="container">
        <router-link class="navbar-brand" to="/">联邦公报动态追踪系统</router-link>
      </div>
    </nav>
    <div class="container mt-5">
      <div class="login-container">
        <h1 class="login-title">登录</h1>
        <div class="input-group">
          <input type="text" v-model="username" placeholder="输入用户名" class="form-control my-2" />
          <input type="password" v-model="password" placeholder="输入密码" class="form-control my-2" />
          <button class="btn btn-primary btn-block my-3"@click="login">登录</button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import { useStore } from 'vuex';
import {computed, ref} from "vue";
import { useRouter } from "vue-router";

export default {
  name: 'Home',
  setup() {
    const store = useStore();
    const router = useRouter();  // 新增这一行
    const username = ref('');
    const password = ref('');
    const BASE_URL = computed(() => store.state.BASE_URL);

    const login = async () => {
      try {
        const response = await axios.post(`${BASE_URL.value}/api/login`, {
          username: username.value,
          password: password.value,
        });
        if (response.data.role === 'ADMIN') {
          store.commit('setAdmin', true);
          router.push('/dashboard');  // 修改这一行
        } else if (response.data.role === 'USER') {
          store.commit('setAdmin', false);
          router.push('/user-interface');  // 修改这一行
        }
      } catch (error) {
        if (error.response) {
          console.log(BASE_URL.value)
          alert(error.response.data.message || '登录失败，请重试。');
        } else {
          alert('无法连接到服务器，请检查您的网络或联系管理员。');
        }
      }
    };

    return {
      username,
      password,
      login
    };
  }
};
</script>



<style scoped>

/* 根据您的设计风格适当添加CSS样式 */
.home {
  margin-top: 100px;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.navbar {
  margin-bottom: 2rem;
  text-align: center;
}

.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 2rem;
  background: #f8f9fa;
  border-radius: 0.5rem;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
}

.login-title {
  text-align: center;
  margin-bottom: 2rem;
}

.input-group {
  display: flex;
  flex-direction: column;
}

.form-control {
  padding: 0.75rem;
  margin-bottom: 1rem;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  font-size: 1rem;
}

.btn-primary {
  width: 100%;
  padding: 1rem;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  transition: background-color 0.2s;
  text-align: center;
  margin-top: 2rem;
}
.btn-primary:hover {
  background-color: #0056b3;
}
</style>

