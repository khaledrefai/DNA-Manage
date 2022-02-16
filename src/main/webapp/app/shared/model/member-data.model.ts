import dayjs from 'dayjs';

export interface IMemberData {
  id?: number;
  testDate?: string | null;
  userId?: number | null;
  userName?: string | null;
  userGender?: string | null;
  userBirthday?: string | null;
  userAge?: number | null;
  userHeight?: number | null;
  orderDate?: string | null;
}

export const defaultValue: Readonly<IMemberData> = {};
