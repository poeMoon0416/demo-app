<template>
  <v-container id="login-area">
    <p v-if="msg" class="msg">{{ msg }}</p>
    <!-- v-formのsubmit等: https://vuetifyjs.com/ja/components/forms/#section-30eb30fc30eb -->
    <v-form @submit.prevent @submit="login" method="post">
      <v-text-field v-model="username" label="ユーザー名" block />
      <v-text-field v-model="password" label="パスワード" block />
      <v-btn type="submit" block>ログイン</v-btn>
    </v-form>
  </v-container>
</template>

<script setup lang="ts">
import router from "@/router";
import type { User } from "@/types/User";

const username = ref<string>("");
const password = ref<string>("");
const user = computed<User>(() => {
  return {
    username: username.value,
    password: password.value,
  };
});
const msg = ref<string>("");

async function login() {
  // FormData: https://developer.mozilla.org/ja/docs/Web/API/FormData
  const formData = new FormData();
  formData.append("username", user.value.username);
  formData.append("password", user.value.password);

  const res = await fetch("http://localhost:8080/login-page", {
    credentials: "include",
    method: "post",
    // headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: formData,
    // body: `username=${username.value}&password=${password.value}`,
  });

  if (!res.ok) {
    // text(): https://developer.mozilla.org/ja/docs/Web/API/Request/text
    msg.value = await res.text();
    return;
  }

  // Vue Router: https://v3.router.vuejs.org/ja/guide/essentials/navigation.html
  router.push("/hello");
}
</script>

<style lang="css" scoped>
#login-area {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

form {
  width: 30%;
}

.msg {
  color: red;
}
</style>
