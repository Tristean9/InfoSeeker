<template>
  <div>
    <!-- 数据呈现 -->
    <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>日期</th>
            <th>机构</th>
            <th>类型</th>
            <th>中文主题</th>
            <th>subject</th>
          </tr>
        </thead>
      <tbody>
        <tr v-for="uniqueDocument in uniqueDocuments" :key="uniqueDocument.id" >
          <td>{{ uniqueDocument.id }}</td>
          <td>{{ uniqueDocument.dateEn }}</td>
          <td>{{ uniqueDocument.agencyEn }}</td>
          <td>{{ uniqueDocument.typeEn }}</td>
          <td>{{ uniqueDocument.subjectZh }}</td>
          <td>{{ uniqueDocument.subjectEn }}</td>
          <td><button @click.stop="selectDocument(uniqueDocument)">查看详情</button></td>
        </tr>
      </tbody>
    </table>
    <div>共 {{uniqueDocuments.length}} 条结果</div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import router from "@/router/index.js";
import {computed} from "vue";

export default {
  props: {
    documents: Array,
  },
  setup(props) {
    const store = useStore();

    const selectDocument = (document) => {
      store.commit('setCurrentDocument', document); // 存储文档到 Vuex
      router.push({name: 'DocumentDetail'}); // 跳转到详细信息页面
    }
    const uniqueDocuments = computed(() => {
      const uniqueIds = new Set();
      return props.documents.filter(document => {
        if (uniqueIds.has(document.id)) {
          return false;
        } else {
          uniqueIds.add(document.id);
          return true;
        }
      });
    })

    return {
      selectDocument,
      uniqueDocuments
    };
  }
};
</script>
<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

table, th, td {
  border: 1px solid black;
}

th, td {
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

/* 可点击的表格行 */
tbody tr:hover {
  background-color: #f5f5f5;
  cursor: pointer;
}
</style>