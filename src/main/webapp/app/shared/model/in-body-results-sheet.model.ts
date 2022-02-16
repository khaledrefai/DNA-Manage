import dayjs from 'dayjs';

export interface IInBodyResultsSheet {
  id?: number;
  userId?: number | null;
  datetimes?: string | null;
  orderDate?: string | null;
  inbodyImage?: string | null;
}

export const defaultValue: Readonly<IInBodyResultsSheet> = {};
