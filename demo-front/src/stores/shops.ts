import type { CustomerSelection, Customer } from "@/types/shops/Customer";
import type { Sale } from "@/types/shops/Sale";
import { defineStore } from "pinia";

// https://pinia.vuejs.org/introduction.html
// 特に You can even use a function (similar to a component setup()) のサンプルを参照
export const useShopsStore = defineStore("shops", () => {
  // state
  const customers = ref<Customer[]>([]);

  const customerSelections = ref<CustomerSelection[]>([]);

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
    const res = await fetch(`http://localhost:8080/customer?id=${id}`);
    const obj = await res.json();
    customers.value = [obj];
  }

  const findSales = async (id: number) => {
    fetch(`http://localhost:8080/sales?id=${id}`)
      .then((res) => res.json())
      .then((obj) => (sale.value = obj));
  };

  // return
  return {
    customers,
    customerSelections,
    sale,
    listCustomers,
    findCustomer,
    findSales,
  };
});
