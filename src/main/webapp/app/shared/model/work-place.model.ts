export interface IWorkPlace {
  id?: number;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<IWorkPlace> = {};
