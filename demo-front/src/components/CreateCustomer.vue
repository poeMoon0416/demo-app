<template>
  <h2>CreateCustomer</h2>
  <!-- 条件付きレンダリング: https://ja.vuejs.org/guide/essentials/conditional.html -->
  <p v-if="!isError && message" style="color: green">{{ message }}</p>
  <p v-if="isError && message" style="color: red">{{ message }}</p>
  <!-- v-form: https://vuetifyjs.com/ja/components/forms/ -->
  <v-form>
    <v-text-field v-model="name" label="顧客名" />
    <v-btn @click="createCustomer">登録</v-btn>
  </v-form>
  <v-data-table :items="[result]" />
</template>

<script setup lang="ts">
import type { Customer } from "@/types/shops/Customer";
import type { Sale } from "@/types/shops/Sale";

const id = ref<number>(-1);

const name = ref<string>("");

const sales = ref<Sale[]>([]);

const customer = computed<Customer>(() => {
  return { id: id.value, name: name.value, sales: sales.value };
});

const result = ref<Customer>({ id: -1, name: "", sales: [] });

// 今後バリデーションでエラーメッセージが複数にはなりうる
const message = ref<string>("");

const isError = ref<boolean>(false);

// Content-Typeがapplication/jsonなのでCORSでプリフライトリクエストがとぶ
async function createCustomer() {
  const res = await fetch(`http://localhost:8080/create-customer`, {
    credentials: "include",
    method: "post",
    headers: {
      "Content-Type": "application/json;charset=UTF-8",
    },
    body: JSON.stringify(customer.value),
  });

  if (!res.ok) {
    message.value = "顧客情報の登録に失敗しました。";
    isError.value = true;
    return;
  }

  result.value = await res.json();
  message.value = "顧客情報の登録に成功しました。";
  isError.value = false;
}
</script>
