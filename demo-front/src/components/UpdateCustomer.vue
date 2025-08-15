<template>
  <!-- OPTIONSは最初しかとばないので確認する際は注意 -->
  <h2>UpdateCustomer</h2>

  <p v-if="!isError && msg" class="success-msg">{{ msg }}</p>
  <p v-if="isError && msg" class="error-msg">{{ msg }}</p>

  <v-form>
    <v-number-input v-model="id" label="顧客ID" />
    <v-text-field v-model="name" label="顧客名" />
    <v-btn @click="updateCustomer">更新</v-btn>
  </v-form>

  <v-data-table :items="[resultCustomer]" />
</template>

<script setup lang="ts">
import type { Customer } from "@/types/shops/Customer";
import type { Sale } from "@/types/shops/Sale";

const id = ref<number>(-1);
const name = ref<string>("");
const sales = ref<Sale[]>([]);
const inputCustomer = computed<Customer>(() => {
  return {
    id: id.value,
    name: name.value,
    sales: sales.value,
  };
});
const resultCustomer = ref<Customer>({
  id: -1,
  name: "",
  sales: [],
});
const msg = ref<string>("");
const isError = ref<boolean>(false);

async function updateCustomer() {
  const res = await fetch(`http://localhost:8080/update-customer/${id.value}`, {
    credentials: "include",
    method: "put",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(inputCustomer.value),
  });

  if (res.status === 404) {
    msg.value = "存在しないIDです";
    isError.value = true;
    return;
  }

  if (res.status / 100 !== 2) {
    msg.value = "顧客情報の更新に失敗しました";
    isError.value = true;
    return;
  }

  resultCustomer.value = await res.json();
  msg.value = "顧客情報の更新に成功しました";
  isError.value = false;
}
</script>

<!-- コンポーネント内だけのCSS適用: https://ja.vuejs.org/api/sfc-css-features -->
<style lang="css" scoped>
.success-msg {
  color: green;
}
.error-msg {
  color: red;
}
</style>
