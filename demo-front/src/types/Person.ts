export interface Person {
  id: number;
  email: string;
  weight: number;
  profile: string;
  prefecture: string;
  // JSのDateは時刻を含む: https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Date
  createdDateTime: Date;
}
