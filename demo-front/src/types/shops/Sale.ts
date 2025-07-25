import type { Customer } from "@/types/shops/Customer";

// interfaceとtypeは必ずしも明確な使い分けの基準があるわけではないようだ
// https://typescriptbook.jp/reference/object-oriented/interface/interface-vs-type-alias
export interface Sale {
  id: number;
  price: number;
  customer: Customer;
}
