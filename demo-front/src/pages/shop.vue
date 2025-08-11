<!-- pagesの下に配置すればルーティングされる -->
<template>
  <h1>Shop</h1>

  <FindSale />

  <FindSaleView />

  <FindCustomer />

  <h2>CreateCustomer</h2>

  <h2>UpdateCustomer</h2>

  <h2>DeleteCustomer</h2>
</template>

<script setup lang="ts">
// imports components
import FindCustomer from "@/components/FindCustomer.vue";
import FindSale from "@/components/FindSale.vue";
import FindSaleView from "@/components/FindSaleView.vue";
</script>
