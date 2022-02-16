import dayjs from 'dayjs';
import { ICaseSampleType } from 'app/shared/model/case-sample-type.model';
import { ISampleStatus } from 'app/shared/model/sample-status.model';

export interface ICaseSample {
  id?: number;
  sampleId?: number | null;
  fullNameAr?: string | null;
  fullNameEn?: string | null;
  natAr?: string | null;
  natEn?: string | null;
  uid?: string | null;
  emiratesId?: string | null;
  exhibit?: string | null;
  gender?: string | null;
  dateOfBirth?: string | null;
  dueDate?: string | null;
  recievedDate?: string | null;
  sampleNotes?: string | null;
  addBy?: number | null;
  addDate?: string | null;
  updateBy?: number | null;
  updateDate?: string | null;
  attachmentContentType?: string | null;
  attachment?: string | null;
  caseNumber?: string | null;
  barcodeNumber?: string | null;
  caseType?: string | null;
  policeStation?: string | null;
  reportNumber?: string | null;
  testEndDate?: string | null;
  caseNote?: string | null;
  caseSampleType?: ICaseSampleType | null;
  sampleStatus?: ISampleStatus | null;
}

export const defaultValue: Readonly<ICaseSample> = {};
