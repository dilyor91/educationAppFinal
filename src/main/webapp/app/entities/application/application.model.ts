import dayjs from 'dayjs/esm';
import { ICourse } from 'app/entities/course/course.model';
import { IGroups } from 'app/entities/groups/groups.model';
import { GenderEnum } from 'app/entities/enumerations/gender-enum.model';
import { AppStatusEnum } from 'app/entities/enumerations/app-status-enum.model';

export interface IApplication {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  middleName?: string | null;
  birthday?: dayjs.Dayjs | null;
  gender?: GenderEnum | null;
  passportNo?: string | null;
  nationality?: string | null;
  occupation?: string | null;
  address?: string | null;
  mobPhone?: string | null;
  homePhone?: string | null;
  notificationMethod?: string | null;
  certificateNo?: string | null;
  certificateGivenDate?: dayjs.Dayjs | null;
  status?: AppStatusEnum | null;
  course?: Pick<ICourse, 'id'> | null;
  groups?: Pick<IGroups, 'id'> | null;
}

export type NewApplication = Omit<IApplication, 'id'> & { id: null };
