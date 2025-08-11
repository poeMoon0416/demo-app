<template>
  <h2>FindSaleView</h2>
  <v-number-input v-model="saleViewId" label="SaleView Id:" />
  <v-btn @click="findSaleView">検索</v-btn>
  <pre>
    <code>
      {{ saleView }}
    </code>
  </pre>
</template>

<script setup lang="ts">
// imports and useStore()
import { useShopsStore } from "@/stores/shops";
import type { SaleView } from "@/types/shops/Sale";
const store = useShopsStore();

// SaleView
const saleViewId = ref<number>(-1);

const findSaleView = async () => {
  store.findSaleView(saleViewId.value);
};

const saleView = computed<SaleView>(() => store.saleView);
</script>
