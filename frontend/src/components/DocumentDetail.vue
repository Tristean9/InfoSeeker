<!-- DocumentDetail.vue -->
<template>
  <div class="document-container">
    <form @submit.prevent="submitForm" class="form-table">
      <div class="form-row">
        <label for="type">type:</label>
        <input type="text" id="typeEn" v-model="document.typeEn" readonly class="readonly-input" />
        <label for="type">类型:</label>
        <input type="text" id="typeZh" v-model="document.typeZh" readonly class="readonly-input" />

      </div>
      <div class="form-row">
        <label for="date">date:</label>
        <input type="text" id="dateEn" v-model="document.dateEn" readonly class="readonly-input" />

        <label for="date">日期:</label>
        <input type="text" id="dateZh" v-model="document.dateZh" readonly class="readonly-input" />

      </div>
      <div class="form-row">

        <label for="agency">agency:</label>
        <input type="text" id="agencyEn" v-model="document.agencyEn" :readonly="!isAdmin" />

        <label for="agency">机构:</label>
        <input type="text" id="agencyZh" v-model="document.agencyZh" :readonly="!isAdmin" />
      </div>

      <div class="form-row">
        <label for="subjectEn">subject:</label>
        <input type="text" id="subjectEn" v-model="document.subjectEn" :readonly="!isAdmin" />

        <label for="subjectZh">中文主题:</label>
        <input type="text" id="subjectZh" v-model="document.subjectZh" :readonly="!isAdmin" />
      </div>

      <div class="form-row">
        <label for="summaryEn">summary:</label>
        <textarea id="summaryEn" v-model="document.summaryEn" readonly class="readonly-input"></textarea>

        <label for="summaryZh">摘要:</label>
        <textarea id="summaryZh" v-model="document.summaryZh" :readonly="!isAdmin"></textarea>
      </div>

      <div class="form-row content">
        <label for="contentEn">content:</label>
        <textarea id="contentEn" v-model="document.contentEn" readonly class="readonly-input input-content"></textarea>

        <label for="contentZh content">内容:</label>
        <textarea id="contentZh" v-model="document.contentZh" :readonly="!isAdmin" class="input-content"></textarea>
      </div>

      <!-- 只有管理员才显示保存更改按钮 -->
      <button v-if="isAdmin" type="submit" class="submit-button">保存更改</button>
    </form>
  </div>
</template>

<script>
import {ref, computed} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import {useStore} from "vuex";
import axios from "axios";

export default {
  setup() {
    const store = useStore();
    // 从 Vuex 获取当前文档
    const document = computed(() => store.state.currentDocument)
    console.log("document:" , document.value)

    const BASE_URL = computed(() => store.state.BASE_URL);
    const isAdmin = computed(() => store.state.isAdmin);

    const updateDocument = async () => {
      try {

        const response = await axios.put(`${BASE_URL.value}/admin/update-document`, document.value);
        if (response.status === 200 || response.status === 204) {
          // 更新前端的记录列表
        alert('更新成功')
        } else {
          console.error('更新失败:', response);
        }
      } catch (error) {
        console.error('请求失败:', error);
      }
    };
    const submitForm = () => {
      // 只有 isAdmin 为 true 时才调用 updateDocument
      if (isAdmin.value) {
        updateDocument();
      }
    };
    console.log(document.value)
    return { document, isAdmin, submitForm, updateDocument };
  }
};
</script>

<style scoped>
.document-container {
  display: flex;

  justify-content: space-between;
  margin: 2rem;
  width: 100%;
  height: 100%;
}

.form-table {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.form-row {
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 2rem;
}
.content {
  height: 200px;
}

label {
  font-weight: bold;
  margin-right: 0.5rem;
  white-space: nowrap; /* 防止标签文字换行 */
}

input[type="text"],
textarea {
  width: calc(50% - 4rem); /* 减去标签宽度和一些内边距 */
  padding: 0.5rem;
  border: none;
  background: transparent;
  outline: none;
  font-family: inherit;
  font-size: 1rem;
  resize: none;
}

.input-content{
  height: 100%;
}
.readonly-input,
textarea[readonly] {
  color: #6c757d;
}

/* 样式调整以适应小屏幕 */
@media (max-width: 768px) {
  .document-container {
    flex-direction: column;
  }

  .form-row {
    flex-direction: column;
    align-items: flex-start;
  }

  input[type="text"],
  textarea {
    width: 100%;
    margin-bottom: 1rem;
  }

  label {
    margin-bottom: 0.5rem;
  }
}

/* 管理员提交按钮样式 */
.submit-button {
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

.submit-button:hover {
  background-color: #0056b3;
}
</style>

