export interface ISampleType {
  id?: number;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<ISampleType> = {};
