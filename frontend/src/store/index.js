// src/store/index.js
import { createStore } from 'vuex';

export default createStore({
  state() {
    return {
      isAdmin: false,
      BASE_URL: 'http://localhost:8080',
      currentDocument: null

    };
  },
  mutations: {
    setAdmin(state, isAdmin) {
      state.isAdmin = isAdmin;
    },
    setCurrentDocument(state, document) {
      state.currentDocument = document;
    },
  },
  actions: {
    login({ commit }, credentials) {
      // 这里发送登录请求并处理响应...
      // 根据响应来更新isAdmin状态
      if (credentials.role === 'ADMIN') {
        commit('setAdmin', true);
      } else {
        commit('setAdmin', false);
      }
    }
  }
});
