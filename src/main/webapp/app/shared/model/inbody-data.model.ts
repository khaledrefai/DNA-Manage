export interface IInbodyData {
  id?: number;
  userID?: number | null;
  columnName?: string | null;
  columnValue?: string | null;
}

export const defaultValue: Readonly<IInbodyData> = {};
