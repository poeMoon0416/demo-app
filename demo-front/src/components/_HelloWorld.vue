<template>
  <v-data-table :items="fetchData" />
  <v-data-table :items="fetchDataSub" />
  <v-container class="fill-height" max-width="900">
    <div>
      <v-img
        class="mb-4"
        height="150"
        src="@/assets/logo.png"
      />

      <div class="mb-8 text-center">
        <div class="text-body-2 font-weight-light mb-n1">Welcome to</div>
        <h1 class="text-h2 font-weight-bold">Vuetify</h1>
      </div>

      <v-row>
        <v-col cols="12">
          <v-card
            class="py-4"
            color="surface-variant"
            image="https://cdn.vuetifyjs.com/docs/images/one/create/feature.png"
            prepend-icon="mdi-rocket-launch-outline"
            rounded="lg"
            variant="tonal"
          >
            <template #image>
              <v-img position="top right" />
            </template>

            <template #title>
              <h2 class="text-h5 font-weight-bold">
                Get started
              </h2>
            </template>

            <template #subtitle>
              <div class="text-subtitle-1">
                Change this page by updating <v-kbd>{{ `<HelloWorld />` }}</v-kbd> in <v-kbd>components/HelloWorld.vue</v-kbd>.
              </div>
            </template>
          </v-card>
        </v-col>

        <v-col v-for="link in links" :key="link.href" cols="6">
          <v-card
            append-icon="mdi-open-in-new"
            class="py-4"
            color="surface-variant"
            :href="link.href"
            :prepend-icon="link.icon"
            rel="noopener noreferrer"
            rounded="lg"
            :subtitle="link.subtitle"
            target="_blank"
            :title="link.title"
            variant="tonal"
          />
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script setup lang="ts">
  import { ref } from 'vue'
  const fetchData = ref([])
  // awaitは非同期関数の中でしか使えない(これだと何も表示されない状態で処理が停止する)
  // const res = await fetch('http://localhost:8080')
  // const obj = await res.json()
  // fetchData.value = obj
  onMounted(async () => {
    const res = await fetch('http://localhost:8080')
    const obj = await res.json()
    fetchData.value = obj
  })
  // 非同期関数を定義してトップレベルで実行はok
  // async function getFetchData () {
  //   const res = await fetch('http://localhost:8080')
  //   const obj = await res.json()
  //   fetchData.value = obj
  // }
  // getFetchData()
  // プロミスチェーンならトップレベル(同期処理)の中でもok
  // fetch('http://localhost:8080').then(res => res.json()).then(obj => fetchData.value = obj)

  const fetchDataSub = ref([])
  onMounted(async () => {
    const res = await fetch('http://localhost:8081')
    const obj = await res.json()
    fetchDataSub.value = obj
  })

  const links = [
    {
      href: 'https://vuetifyjs.com/',
      icon: 'mdi-text-box-outline',
      subtitle: 'Learn about all things Vuetify in our documentation.',
      title: 'Documentation',
    },
    {
      href: 'https://vuetifyjs.com/introduction/why-vuetify/#feature-guides',
      icon: 'mdi-star-circle-outline',
      subtitle: 'Explore available framework Features.',
      title: 'Features',
    },
    {
      href: 'https://vuetifyjs.com/components/all',
      icon: 'mdi-widgets-outline',
      subtitle: 'Discover components in the API Explorer.',
      title: 'Components',
    },
    {
      href: 'https://discord.vuetifyjs.com',
      icon: 'mdi-account-group-outline',
      subtitle: 'Connect with Vuetify developers.',
      title: 'Community',
    },
  ]
</script>
