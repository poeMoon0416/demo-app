<template>
  <h2>FindSale</h2>
  <!-- https://vuetifyjs.com/ja/components/number-inputs/ -->
  <v-number-input v-model="salesId" label="Sales Id:" />
  <v-btn @click="findSales">検索</v-btn>
  <pre>
    <code>
      {{ sale }}
    </code>
  </pre>
</template>

<script setup lang="ts">
// imports and useStore()
import { useShopsStore } from "@/stores/shops";
import type { Sale } from "@/types/shops/Sale";
const store = useShopsStore();

// Sales
// https://ja.vuejs.org/guide/essentials/reactivity-fundamentals.html
const salesId = ref<number>(-1);

const findSales = async () => {
  store.findSales(salesId.value);
};

// https://ja.vuejs.org/guide/essentials/computed.html
const sale = computed<Sale>(() => store.sale);
</script>
