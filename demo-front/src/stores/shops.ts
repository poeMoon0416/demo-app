import type { CustomerSelection, Customer } from "@/types/shops/Customer";
import type { Sale, SaleView } from "@/types/shops/Sale";
import { defineStore } from "pinia";

// https://pinia.vuejs.org/introduction.html
// 特に You can even use a function (similar to a component setup()) のサンプルを参照
export const useShopsStore = defineStore("shops", () => {
  // state
  const customers = ref<Customer[]>([]);

  const customerSelections = ref<CustomerSelection[]>([]);

  const sale = ref<Sale>({
    id: -1,
    price: -1,
    customer: {
      id: -1,
      name: "",
      sales: [],
    },
  });

  const saleView = ref<SaleView>({
    id: -1,
    price: -1,
    customerName: "",
  });

  // actions
  const listCustomers = async () => {
    fetch("http://localhost:8080/customers", { credentials: "include" })
      .then((res) => res.json())
      .then((obj: Customer[]) => {
        customers.value = obj;
        customerSelections.value = obj.map((customer) => {
          const { name, id } = customer;
          return { name, id } as CustomerSelection;
        });
      });
  };

  async function findCustomer(id: number) {
    // console.log(id);
    const res = await fetch(`http://localhost:8080/customer?id=${id}`, {
      credentials: "include",
    });
    const obj = await res.json();
    customers.value = [obj];
  }

  const findSales = async (id: number) => {
    fetch(`http://localhost:8080/sales?id=${id}`, { credentials: "include" })
      .then((res) => res.json())
      .then((obj) => (sale.value = obj));
  };

  const findSaleView = async (id: number) => {
    fetch(`http://localhost:8080/sales-view/${id}`, { credentials: "include" })
      .then((res) => res.json())
      .then((obj: { 売上情報ID: number; 売上高: number; 顧客名: string }) => {
        // JSONとTypeScriptのDTOのプロパティ名が異なっている場合でもマップ可能
        const { 売上情報ID, 売上高, 顧客名 } = obj;
        saleView.value = {
          id: 売上情報ID,
          price: 売上高,
          customerName: 顧客名,
        };
      });
  };

  // return
  return {
    customers,
    customerSelections,
    sale,
    saleView,
    listCustomers,
    findCustomer,
    findSales,
    findSaleView,
  };
});
