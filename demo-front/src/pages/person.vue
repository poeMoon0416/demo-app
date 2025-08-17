<template>
  <body>
    <h1>create-person</h1>

    <!-- v-for: https://ja.vuejs.org/guide/essentials/list -->
    <ul v-if="successMessages.length > 0" id="success-messages">
      <li v-for="successMessage in successMessages">{{ successMessage }}</li>
    </ul>
    <ul v-if="errorMessages.length > 0" id="error-messages">
      <li v-for="errorMessage in errorMessages">{{ errorMessage }}</li>
    </ul>

    <h2>個人情報作成</h2>
    <v-form>
      <v-text-field v-model="email" label="メールアドレス" />
      <v-text-field v-model="weight" label="体重" />
      <v-textarea v-model="profile" label="自己紹介"></v-textarea>
      <v-text-field v-model="prefecture" label="出身地(都道府県)" />
      <v-btn @click="createPerson">作成</v-btn>
    </v-form>

    <h2>個人情報一覧</h2>
    <v-data-table :items="persons" />
  </body>
</template>

<script setup lang="ts">
import type { CreatePersonView } from "@/types/CreatePersonView";
import type { Person } from "@/types/Person";

const email = ref<string>("");
const weight = ref<number>(-1);
const profile = ref<string>("");
const prefecture = ref<string>("");

const createPersonView = computed<CreatePersonView>(() => {
  return {
    email: email.value,
    weight: weight.value,
    profile: profile.value,
    prefecture: prefecture.value,
  };
});
const persons = ref<Person[]>([]);

const successMessages = ref<string[]>([]);
const errorMessages = ref<string[]>([]);

async function listPerson() {
  const res = await fetch("http://localhost:8080/rest-list-person", {
    mode: "cors",
    credentials: "include",
    method: "get",
    headers: { "Content-Type": "application/json" },
  });

  if (!res.ok) {
    errorMessages.value = ["個人情報を取得できません", ...errorMessages.value];
    successMessages.value = [];
    return;
  }

  persons.value = await res.json();
}
listPerson();

async function createPerson() {
  const res = await fetch("http://localhost:8080/rest-create-person", {
    mode: "cors",
    credentials: "include",
    method: "post",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(createPersonView.value),
  });

  if (res.status === 400) {
    errorMessages.value = await res.json();
    successMessages.value = [];
    return;
  }

  if (!res.ok) {
    errorMessages.value = ["個人情報を作成できません", ...errorMessages.value];
    successMessages.value = [];
    return;
  }

  successMessages.value = await res.json();
  errorMessages.value = [];
  listPerson();
}
</script>

<style lang="css" scoped>
#success-messages {
  color: green;
}
#error-messages {
  color: red;
}
</style>
