<template>
  <div class="search-container">
    <div class="search-entry" v-for="(search, index) in searches" :key="index">
      <select v-model="search.type">
        <option value="id">id</option>
        <option value="dateEn">date</option>
        <option value="dateZh">日期</option>
        <option value="typeEn">type</option>
        <option value="typeZh">类型</option>
        <option value="agencyEn">agency</option>
        <option value="agencyZh">机构</option>
        <option value="subjectEn">subjectEn</option>
        <option value="subjectZh">subjectZh</option>
        <option value="summaryEn">summaryEn</option>
        <option value="summaryZh">summaryZh</option>
        <option value="contentEn">contentEn</option>
        <option value="contentZh">contentZh</option>
      </select>

      <input type="text" v-model="search.query" placeholder="请输入检索内容" />
       <div class="buttons-container">

      <button v-if="index === searches.length - 1 && searches.length < 13" @click="addSearchEntry">
        添加
      </button>

      <button v-if="searches.length > 1" @click="removeSearchEntry(index)">
        移除
      </button>
       </div>
    </div>

    <button @click="submitSearch">检索</button>
  </div>

  <!-- 检索结果展示 -->
  <DocumentDisplay
    v-if="searchResults.length > 0"
    :documents="searchResults"
    :is-admin="isAdmin"
  />
</template>

<script>
import {computed, ref} from 'vue';
import { useStore } from 'vuex';
import DocumentDisplay from "@/components/DocumentDisplay.vue";

export default {
  components: {
    DocumentDisplay
  },
  setup() {

    const store = useStore();
    const BASE_URL = computed(() => store.state.BASE_URL).value;
    const searches = ref([
      { type: 'subjectEn', query: '' }
    ]);
    const searchResults = ref([]);

    // 使用 computed 从 Vuex store 获取 isAdmin 状态
    const isAdmin = computed(() => store.state.isAdmin).value;

    function addSearchEntry() {
      searches.value.push({ type: 'subjectEn', query: '' });
    }

    function removeSearchEntry(index) {
      searches.value.splice(index, 1);
    }

    async function submitSearch() {
      // 首先，检查所有的搜索入口是否有空的查询字符串
      const hasEmptyQuery = searches.value.some(search => !search.query.trim());
      if (hasEmptyQuery) {
        // 如果有空的查询，弹出警告并且不执行后续的搜索请求
        alert('检索框的内容不能为空，请输入检索内容。');
        return;
      }
      // 如果所有的检索框都有内容，则继续执行检索操作
      const searchParams = searches.value.reduce((params, entry) => {
        params[entry.type] = entry.query;
        return params;
      }, {});

      try {
        let endpoint;
        if (isAdmin.value){
          endpoint = `${BASE_URL}/admin/search`;
        }else {
          endpoint = `${BASE_URL}/user/search`;
        }
        const response = await fetch(endpoint, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(searchParams)
        });
        if (response.ok) {
          searchResults.value = await response.json();
          console.log(searchResults.value)
          if (searchResults.value.length === 0){
            console.error("检索结果为空")
          }
        } else {
          console.error('Search request failed:', response);
        }
      } catch (error) {
        console.error('Error fetching search results:', error);
      }
    }

    return {
      searches,
      searchResults,
      isAdmin,
      addSearchEntry,
      removeSearchEntry,
      submitSearch
    };
  }
};
</script>

<style scoped>
.search-container {

  max-width: 800px;
  margin: 50px auto;
  padding: 20px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  background: #fff;
}

.search-entry {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
}

.search-entry > * {
  flex: 1;
  margin: 2px;
}

.search-entry select,
.search-entry input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
}

/* 创建按钮容器，确保添加和移除按钮在同一行并对齐 */
.buttons-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

button {
  flex-shrink: 0;
  padding: 10px 20px;
  border: none;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  border-radius: 4px;
  font-size: 16px;
  transition: background-color 0.2s, box-shadow 0.2s;
}

button:hover {
  background-color: #0056b3;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

button:active {
  background-color: #003875;
}

/* 响应式布局调整 */
@media (max-width: 768px) {
  .search-entry {
    flex-direction: column;
  }

  .search-entry > * {
    width: 100%;
    margin-top: 10px;
  }

  .buttons-container {
    flex-direction: row;
    justify-content: space-between;
  }

  button:last-of-type {
    width: 100%;
    margin-top: 20px;
  }
}
</style>

