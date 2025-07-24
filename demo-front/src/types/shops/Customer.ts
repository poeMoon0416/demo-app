import type { Sale } from "@/types/shops/Sale";

export interface Customer {
  id: number;
  name: string;
  sales: Sale[];
}
