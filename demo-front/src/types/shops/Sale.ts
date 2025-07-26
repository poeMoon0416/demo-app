import type { Customer } from "@/types/shops/Customer";

// interfaceとtypeは必ずしも明確な使い分けの基準があるわけではないようだ
// https://typescriptbook.jp/reference/object-oriented/interface/interface-vs-type-alias
export interface Sale {
  id: number;
  price: number;
  customer: Customer;
}

// Pick: https://typescriptbook.jp/reference/type-reuse/utility-types/pick
// 型の合成: https://typescriptbook.jp/reference/values-types-variables/intersection
// プロパティの型の取得: https://typescriptbook.jp/reference/type-reuse/indexed-access-types
export type SaleView = Pick<Sale, "id" | "price"> & {
  customerName: Customer["name"];
};
// 使用例(全てのプロパティが取得元の型の変更を追跡するようになっている)
// const saleView: SaleView = { id: 1, price: 1000, customerName: "guest" };
