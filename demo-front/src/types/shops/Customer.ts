import type { Sale } from "@/types/shops/Sale";

export interface Customer {
  id: number;
  name: string;
  sales: Sale[];
}

// interfaceだとエラー, PickとOmitはtypeでないと受け取れない
export type CustomerSelection = Pick<Customer, "name" | "id">;
