<template>
  <h1>shop</h1>
  <p>Customers</p>
  <pre>
    <code>
      {{ store.customers }}
    </code>
  </pre>
  <v-data-table :items="store.customers" />
  <p>Sale</p>
  <v-number-input v-model="salesId" label="Sales Id:" />
  <v-btn @click="findSales">検索</v-btn>
  {{ sale }}
</template>

<script setup lang="ts">
import { useShopsStore } from "@/stores/shops";
import type { Sale } from "@/types/shops/Sale";

const store = useShopsStore();

store.listCustomers();

const findSales = () => {
  store.findSales(salesId.value);
};

const salesId = ref<number>(0);

const sale = computed<Sale>(() => store.sale);
</script>
