<!-- pagesの下に配置すればルーティングされる -->
<template>
  <h1>shop</h1>

  <p>Customers</p>
  <!-- https://vuetifyjs.com/ja/components/selects/ -->
  <!--https://ja.vuejs.org/guide/essentials/forms.html  -->
  <!-- return-objectの指定がないとitem-valueのみが返却されるので注意 -->
  <v-select
    :items="store.customerSelections"
    item-title="name"
    item-value="id"
    v-model="currentCustomerSelection"
    return-object
  ></v-select>
  <!-- https://vuetifyjs.com/ja/components/buttons/ -->
  <!-- https://ja.vuejs.org/guide/essentials/event-handling.html -->
  <v-btn @click="findCustomer">検索</v-btn>
  <!-- https://vuetifyjs.com/ja/components/data-tables/basics/ -->
  <v-data-table :items="store.customers" />
  <!-- https://developer.mozilla.org/ja/docs/Web/HTML/Reference/Elements/code#%E8%A3%9C%E8%B6%B3 -->
  <!-- https://ja.vuejs.org/guide/essentials/template-syntax#text-interpolation -->
  <pre>
    <code>
      {{ store.customers }}
    </code>
  </pre>

  <p>Sale</p>
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
import type { CustomerSelection } from "@/types/shops/Customer";
const store = useShopsStore();

// customers
store.listCustomers();

const currentCustomerSelection = ref<CustomerSelection>({
  name: "顧客名を選択してください",
  id: -1,
});

async function findCustomer() {
  store.findCustomer(currentCustomerSelection.value.id);
}

// sales
// https://ja.vuejs.org/guide/essentials/reactivity-fundamentals.html
const salesId = ref<number>(-1);

const findSales = async () => {
  store.findSales(salesId.value);
};

// https://ja.vuejs.org/guide/essentials/computed.html
const sale = computed<Sale>(() => store.sale);
</script>
