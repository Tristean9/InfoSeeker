import { createRouter, createWebHistory } from 'vue-router';

// 导入你的组件
import Home from '../views/Home.vue';
import UserInterface from '../views/UserInterface.vue';
import Dashboard from '../views/Dashboard.vue';
import Track from "@/components/Track.vue";
import SearchBar from "@/components/SearchBar.vue";
import DocumentDetail from "@/components/DocumentDetail.vue";

// 路由定义
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/user-interface',
    name: 'UserInterface',
    component: UserInterface
    // 这里可以添加路由守卫确保只有普通用户可以访问
  },
  {
    path: '/user-interface/searchbar',
    name: "UserSearchBar",
    component: SearchBar
  },

  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard
    // 这里可以添加路由守卫确保只有管理员可以访问
  },
  {
    path: '/dashboard/track',
    name: 'Track',
    component: Track
  },
  {
    path: '/dashboard/searchbar',
    name: "SearchBar",
    component: SearchBar
  },
  {
    path: '/dashboard/searchbar/document-detail',
    name: "DocumentDetail",
    component: DocumentDetail
  }

];

// 创建 router 实例
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

export default router;
