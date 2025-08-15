<template>
  <h2>DeleteCustomer</h2>

  <p v-if="!isError && msg" style="color: green">{{ msg }}</p>
  <p v-if="isError && msg" style="color: red">{{ msg }}</p>

  <v-form>
    <v-number-input v-model="id" label="顧客ID" />
    <v-btn @click="deleteCustomer">削除</v-btn>
  </v-form>
</template>

<script setup lang="ts">
const id = ref<number>(-1);
const msg = ref<string>("");
const isError = ref<boolean>(false);

async function deleteCustomer() {
  const res = await fetch(`http://localhost:8080/delete-customer/${id.value}`, {
    credentials: "include",
    method: "delete",
  });

  if (res.status === 404) {
    msg.value = "顧客情報が存在しません";
    isError.value = true;
    return;
  }

  if (!res.ok) {
    msg.value = "顧客情報の削除に失敗しました";
    isError.value = true;
    return;
  }

  msg.value = "顧客情報の削除に成功しました";
  isError.value = false;
}
</script>
