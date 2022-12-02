import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IPeriod } from 'app/entities/period/period.model';
import { ICourse } from 'app/entities/course/course.model';

export interface IGroups {
  id: number;
  groupNo?: string | null;
  startHour?: dayjs.Dayjs | null;
  endHour?: dayjs.Dayjs | null;
  capacity?: number | null;
  reservedPlace?: number | null;
  status?: boolean | null;
  full?: boolean | null;
  user?: Pick<IUser, 'id'> | null;
  period?: Pick<IPeriod, 'id'> | null;
  course?: Pick<ICourse, 'id'> | null;
}

export type NewGroups = Omit<IGroups, 'id'> & { id: null };
