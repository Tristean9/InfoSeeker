<template>
  <div class="dashboard">
    <h1>管理员界面</h1>
    <nav class="navigation">
        <router-link to="/dashboard/searchbar" class="nav-link">检索界面</router-link>
        <router-link to="/dashboard/track" class="nav-link">动态追踪界面</router-link>
    </nav>
  </div>
</template>


<script>
export default {
  name: 'Dashboard'
};
</script>

<style scoped>
.dashboard {
  text-align: center;
  padding: 2rem;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.dashboard h1 {
  color: #333;
  margin-bottom: 2rem;
}

.navigation {
  display: inline-block;
  background-color: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.nav-link {
  text-decoration: none;
  color: #007bff;
  margin: 0 1rem;
  font-size: 1rem;
  transition: color 0.3s ease;
}

.nav-link:hover {
  color: #0056b3;
}

@media (max-width: 768px) {
  .navigation {
    display: block;
  }

  .nav-link {
    display: block;
    margin: 1rem 0;
  }
}
</style>

