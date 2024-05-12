<template>
  <div class="user-interface">
    <h1 class="title">普通用户界面</h1>
    <nav class="navigation">
      <router-link to="/user-interface/searchbar" class="nav-link">检索界面</router-link>
    </nav>
  </div>
</template>


<script>
import SearchBar from "@/components/SearchBar.vue";
export default {
  name: 'UserInterface',
  components:{
    SearchBar,
  }
};
</script>

<style scoped>
.user-interface {
  margin-top: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #eef1f5;
  padding: 20px;
}

.title {
  color: #2c3e50;
  margin-bottom: 1rem;
  font-size: 2.5rem;
}

.navigation {
  background-color: #ffffff;
  padding: 10px 20px;
  border-radius: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.nav-link {
  text-decoration: none;
  color: #3498db;
  font-size: 1.2rem;
  transition: color 0.3s ease-in-out;
}

.nav-link:not(:last-of-type) {
  margin-right: 1rem;
}

.nav-link:hover,
.nav-link:focus {
  color: #1a5276;
}

@media (max-width: 768px) {
  .title {
    font-size: 2rem;
  }

  .navigation {
    padding: 15px;
    border-radius: 15px;
  }

  .nav-link {
    font-size: 1rem;
  }
}
</style>

