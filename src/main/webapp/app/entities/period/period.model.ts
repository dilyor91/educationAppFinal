import dayjs from 'dayjs/esm';

export interface IPeriod {
  id: number;
  nameUz?: string | null;
  nameRu?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  status?: boolean | null;
}

export type NewPeriod = Omit<IPeriod, 'id'> & { id: null };
