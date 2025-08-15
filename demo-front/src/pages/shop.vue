<!-- pagesの下に配置すればルーティングされる -->
<template>
  <h1>Shop</h1>

  <FindSale />

  <FindSaleView />

  <FindCustomer />

  <CreateCustomer />

  <UpdateCustomer />

  <h2>DeleteCustomer</h2>
</template>

<script setup lang="ts">
// imports components
import CreateCustomer from "@/components/CreateCustomer.vue";
import FindCustomer from "@/components/FindCustomer.vue";
import FindSale from "@/components/FindSale.vue";
import FindSaleView from "@/components/FindSaleView.vue";
import UpdateCustomer from "@/components/UpdateCustomer.vue";
</script>
