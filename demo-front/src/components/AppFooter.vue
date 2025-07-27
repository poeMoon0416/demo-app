<template>
  <v-footer app height="32">
    <p>フッター</p>
  </v-footer>
</template>

<script setup lang="ts"></script>

<style scoped lang="css">
/* p {
    color: red;
} */
p::after {
  content: "";
  display: inline-block;
  height: 1rem;
  width: 1rem;
  border: 0.5rem solid rgba(0, 0, 0, 0);
  border-left: 1rem solid rgb(123, 255, 0);
  margin-left: 0.5rem;
}
</style>
