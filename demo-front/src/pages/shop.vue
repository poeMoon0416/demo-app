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
  <!-- コンポーネントの設定, :propsで設定用の変数にバインド, v-slot:slotで中身の上書き -->
  <v-data-table
    :items="store.customers"
    :headers="customersHeader"
    :loading="customerLoading"
  >
    <!-- https://vuetifyjs.com/ja/components/data-tables/basics/#item-30b930ed30c330c8 -->
    <template v-slot:item="{ item }">
      <!-- <p>空です</p> -->
      <!-- https://vuetifyjs.com/en/styles/colors/ -->
      <!-- https://vuetifyjs.com/ja/styles/text-and-typography/#section-4f7f304465b9 -->
      <tr>
        <td>{{ item.id }}</td>
        <td>{{ item.name }}</td>
        <td>{{ item.sales[0].id }}</td>
        <td
          :class="{
            'bg-grey-lighten-1 font-weight-bold': item.sales[0].price >= 1000,
          }"
        >
          {{ item.sales[0].price }}
        </td>
      </tr>
    </template>
    <!-- https://vuetifyjs.com/ja/components/data-tables/basics/#loading-30b930ed30c330c8 -->
    <template v-slot:loading>
      <!-- https://vuetifyjs.com/en/components/grids/#v-container -->
      <v-container>
        <!-- https://vuetifyjs.com/en/styles/flex/#usage -->
        <v-row class="d-flex justify-center align-center">
          <!-- https://vuetifyjs.com/en/styles/text-and-typography/#usage -->
          <p class="text-h6">ロード中です...</p>
          <!-- https://vuetifyjs.com/en/components/images/#usage -->
          <!-- https://vuetifyjs.com/en/styles/spacing/#usage -->
          <v-img
            max-width="44"
            aspect-ratio="58/59"
            src="@/assets/loading.png"
            class="ma-4"
          />
        </v-row>
      </v-container>
    </template>
  </v-data-table>
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

  <p>SaleView</p>
  <v-number-input v-model="saleViewId" label="SaleView Id:" />
  <v-btn @click="findSaleView">検索</v-btn>
  <pre>
    <code>
      {{ saleView }}
    </code>
  </pre>
</template>

<script setup lang="ts">
// imports and useStore()
import { useShopsStore } from "@/stores/shops";
import type { SaleView, Sale } from "@/types/shops/Sale";
import type { CustomerSelection } from "@/types/shops/Customer";
const store = useShopsStore();

// Customersの状態やイベントハンドラ
store.listCustomers();

const currentCustomerSelection = ref<CustomerSelection>({
  name: "顧客名を選択してください",
  id: -1,
});

async function findCustomer() {
  customerLoading.value = true;
  // https://developer.mozilla.org/ja/docs/Web/API/Window/setTimeout
  // https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Promise/Promise
  // sleep: 全ての非同期関数がPromiseを返すわけではないので、デフォルトで実行を待機できるとは限らないようだ
  await new Promise((resolve) => {
    setTimeout(() => {
      resolve("");
    }, 3000);
  });
  store.findCustomer(currentCustomerSelection.value.id);
  customerLoading.value = false;
}

const customersHeader = [
  { title: "顧客ID", value: "id" },
  { title: "顧客名", value: "name" },
  {
    title: "販売情報",
    children: [
      { title: "販売ID", value: "sales[0].id" },
      { title: "販売価格", value: "sales[0].price" },
    ],
  },
];

const customerLoading = ref<boolean>(false);

// Sales
// https://ja.vuejs.org/guide/essentials/reactivity-fundamentals.html
const salesId = ref<number>(-1);

const findSales = async () => {
  store.findSales(salesId.value);
};

// https://ja.vuejs.org/guide/essentials/computed.html
const sale = computed<Sale>(() => store.sale);

// SaleView
const saleViewId = ref<number>(-1);

const findSaleView = async () => {
  store.findSaleView(saleViewId.value);
};

const saleView = computed<SaleView>(() => store.saleView);
</script>
