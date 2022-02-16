import dayjs from 'dayjs';
import { IDnaProfileType } from 'app/shared/model/dna-profile-type.model';
import { IProjectType } from 'app/shared/model/project-type.model';
import { ISampleType } from 'app/shared/model/sample-type.model';
import { IWorkPlace } from 'app/shared/model/work-place.model';
import { ISampleStatus } from 'app/shared/model/sample-status.model';

export interface IInhouseSample {
  id?: number;
  sampleId?: number | null;
  empGrpNo?: number | null;
  fullNameAr?: string | null;
  fullNameEn?: string | null;
  natAr?: string | null;
  natEn?: string | null;
  uid?: string | null;
  emiratesId?: string | null;
  exhibit?: string | null;
  gender?: string | null;
  dateOfBirth?: string | null;
  batchDate?: string | null;
  collectionDate?: string | null;
  sampleNotes?: string | null;
  addBy?: number | null;
  addDate?: string | null;
  updateBy?: number | null;
  updateDate?: string | null;
  attachmentContentType?: string | null;
  attachment?: string | null;
  dnaProfileType?: IDnaProfileType | null;
  projectType?: IProjectType | null;
  sampleType?: ISampleType | null;
  workPlace?: IWorkPlace | null;
  sampleStatus?: ISampleStatus | null;
}

export const defaultValue: Readonly<IInhouseSample> = {};
