<template>
  <div class="track">
    <h1 class="title">动态追踪界面</h1>
    <div class="buttons">
      <button @click="startCrawlingAll" class="button">开始爬取、解析和翻译（全部）</button>
      <button @click="startCrawlingToday" class="button">开始爬取、解析和翻译（今天）</button>
      <!--<button @click="buildIndex" class="button">构建索引</button>-->
    </div>
    <div v-if="isLoading" class="loading-overlay">
      <span class="loading-text">正在请求服务，请稍等...</span>
    </div>
    <DocumentDisplay
      v-if="shouldShowDocuments"
      :documents="parsedDocuments"
      :is-admin="isAdmin"
    />
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';
import DocumentDisplay from './DocumentDisplay.vue'; // 确保路径正确
import { computed } from 'vue';
import { useStore } from 'vuex';

export default {
  components: {
    DocumentDisplay
  },
  setup() {

    const parsedDocuments = ref([]);
    const store = useStore();
    const BASE_URL = computed(() => store.state.BASE_URL);
    const isAdmin = computed(() => store.state.isAdmin);
    const singleDocument = ref(null);
    const isLoading = ref(false);
    const shouldShowDocuments = computed(() => {
      return parsedDocuments.value.length > 0;
    });

    const startCrawlingToday = async () => {
      isLoading.value = true; // 设置加载状态
      try {
        const response = await axios.post(`${BASE_URL.value}/admin/crawl-and-process-and-save-today`);
        parsedDocuments.value = response.data; // 假设后端返回处理后的文档列表
      } catch (error) {
        console.error('请求失败:', error);
      } finally {
        isLoading.value = false; // 无论成功还是失败，都取消加载状态
      }
    };

    const startCrawlingAll = async () => {
      isLoading.value = true; // 设置加载状态
      try {
        const response = await axios.post(`${BASE_URL.value}/admin/crawl-and-process-and-save-all`);
        // 处理逻辑
      } catch (error) {
        console.error('请求失败:', error);
      }finally {
        isLoading.value = false; // 无论成功还是失败，都取消加载状态
      }
    };

    const searchOne = async () => {
      try {
        const response = await axios.post(`${BASE_URL.value}/admin/searchOne`);
        singleDocument.value = response.data; // 假设后端返回单个文档的数据
        parsedDocuments.value = [response.data]; // 将单个对象放入数组中
      } catch (error) {
        console.error('检索数据失败:', error);
      }
    };

    const buildIndex = async () => {
      try {
        await axios.post(`${BASE_URL.value}/admin/build-index`);
        alert('索引构建成功！');
      } catch (error) {
        console.error('构建索引失败: ', error);
        alert('索引构建失败！');
      }
    };

    // 返回响应式引用和方法，这样它们就可以在模板中使用
    return {
      parsedDocuments,
      isAdmin,
      isLoading,
      singleDocument,
      shouldShowDocuments,
      startCrawlingToday,
      startCrawlingAll,
      searchOne,
      buildIndex,
    };
  }
};
</script>

<style scoped>
.track {
  margin-top: 100px;
  padding: 20px;
  text-align: center;
}

.title {
  margin-bottom: 30px;
  color: #333;
}

.buttons {
  margin-bottom: 20px;
}

.button {
  margin: 0 10px;
  padding: 10px 20px;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.button:hover {
  background-color: #0056b3;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
}

.loading-text {
  padding: 20px;
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>

