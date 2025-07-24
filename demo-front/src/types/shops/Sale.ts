import type { Customer } from "@/types/shops/Customer";

export interface Sale {
  id: number;
  price: number;
  customer: Customer;
}
