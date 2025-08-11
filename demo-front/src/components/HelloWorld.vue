<template>
  <v-data-table :items="fetchData" />
  <v-data-table :items="fetchDataSub" />
</template>

<script setup lang="ts">
import { ref } from "vue";
const fetchData = ref([]);
// awaitは非同期関数の中でしか使えない(これだと何も表示されない状態で処理が停止する)
// const res = await fetch('http://localhost:8080')
// const obj = await res.json()
// fetchData.value = obj
onMounted(async () => {
  // Cookie等は通常のfetchリクエストには含まれないのでcredentialsの指定が必要
  // https://developer.mozilla.org/ja/docs/Web/HTTP/Guides/CORS#%E8%B3%87%E6%A0%BC%E6%83%85%E5%A0%B1%E3%82%92%E5%90%AB%E3%82%80%E3%83%AA%E3%82%AF%E3%82%A8%E3%82%B9%E3%83%88
  // Cookieは禁止リクエストヘッダーなのでプログラムから直接指定はできないようだ
  // https://developer.mozilla.org/ja/docs/Web/API/Fetch_API/Using_Fetch#%E3%83%98%E3%83%83%E3%83%80%E3%83%BC%E3%81%AE%E8%A8%AD%E5%AE%9A
  const res = await fetch("http://localhost:8080", { credentials: "include" });
  const obj = await res.json();
  fetchData.value = obj;
});
// 非同期関数を定義してトップレベルで実行はok
// async function getFetchData () {
//   const res = await fetch('http://localhost:8080')
//   const obj = await res.json()
//   fetchData.value = obj
// }
// getFetchData()
// プロミスチェーンならトップレベル(同期処理)の中でもok
// fetch('http://localhost:8080').then(res => res.json()).then(obj => fetchData.value = obj)

const fetchDataSub = ref([]);
onMounted(async () => {
  const res = await fetch("http://localhost:8081");
  const obj = await res.json();
  fetchDataSub.value = obj;
});

const links = [
  {
    href: "https://vuetifyjs.com/",
    icon: "mdi-text-box-outline",
    subtitle: "Learn about all things Vuetify in our documentation.",
    title: "Documentation",
  },
  {
    href: "https://vuetifyjs.com/introduction/why-vuetify/#feature-guides",
    icon: "mdi-star-circle-outline",
    subtitle: "Explore available framework Features.",
    title: "Features",
  },
  {
    href: "https://vuetifyjs.com/components/all",
    icon: "mdi-widgets-outline",
    subtitle: "Discover components in the API Explorer.",
    title: "Components",
  },
  {
    href: "https://discord.vuetifyjs.com",
    icon: "mdi-account-group-outline",
    subtitle: "Connect with Vuetify developers.",
    title: "Community",
  },
];
</script>
