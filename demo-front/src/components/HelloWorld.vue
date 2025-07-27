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
  const res = await fetch("http://localhost:8080");
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
