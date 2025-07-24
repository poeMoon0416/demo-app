import type { Customer } from "@/types/shops/Customer";
import type { Sale } from "@/types/shops/Sale";
import { defineStore } from "pinia";

export const useShopsStore = defineStore("shops", () => {
  // state
  const customers = ref<Customer[]>([]);

  const sale = ref<Sale>({
    id: 0,
    price: 0,
    customer: {
      id: 0,
      name: "",
      sales: [],
    },
  });

  // actions
  const listCustomers = async () => {
    fetch("http://localhost:8080/customers")
      .then((res) => res.json())
      .then((obj) => (customers.value = obj));
  };

  const findSales = async (id: number) => {
    fetch(`http://localhost:8080/sales?id=${id}`)
      .then((res) => res.json())
      .then((obj) => (sale.value = obj));
  };

  // return
  return {
    customers,
    sale,
    listCustomers,
    findSales,
  };
});
