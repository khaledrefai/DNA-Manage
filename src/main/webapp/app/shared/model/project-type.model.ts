export interface IProjectType {
  id?: number;
  name?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<IProjectType> = {};
