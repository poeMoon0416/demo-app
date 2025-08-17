<!-- pagesの下に配置すればルーティングされる -->
<template>
  <h1>Shop</h1>

  <FindSale />

  <FindSaleView />

  <FindCustomer />

  <CreateCustomer />

  <UpdateCustomer />

  <DeleteCustomer />
</template>

<script setup lang="ts">
// imports components
import FindSale from "@/components/FindSale.vue";
import FindSaleView from "@/components/FindSaleView.vue";
import FindCustomer from "@/components/FindCustomer.vue";
import CreateCustomer from "@/components/CreateCustomer.vue";
import UpdateCustomer from "@/components/UpdateCustomer.vue";
import DeleteCustomer from "@/components/DeleteCustomer.vue";
</script>
