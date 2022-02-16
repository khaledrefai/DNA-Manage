export interface ISampleStatus {
  id?: number;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<ISampleStatus> = {};
