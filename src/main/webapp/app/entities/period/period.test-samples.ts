import dayjs from 'dayjs/esm';

import { IPeriod, NewPeriod } from './period.model';

export const sampleWithRequiredData: IPeriod = {
  id: 38959,
  nameUz: 'Outdoors Shirt mobile',
  nameRu: 'Future',
  startDate: dayjs('2022-12-02T08:17'),
  endDate: dayjs('2022-12-01T13:57'),
  status: false,
};

export const sampleWithPartialData: IPeriod = {
  id: 9129,
  nameUz: 'Fresh',
  nameRu: 'Triple-buffered supply-chains Wyoming',
  startDate: dayjs('2022-12-02T02:46'),
  endDate: dayjs('2022-12-01T22:06'),
  status: true,
};

export const sampleWithFullData: IPeriod = {
  id: 1182,
  nameUz: 'program port Salad',
  nameRu: 'clear-thinking Handmade',
  startDate: dayjs('2022-12-01T13:07'),
  endDate: dayjs('2022-12-02T03:54'),
  status: false,
};

export const sampleWithNewData: NewPeriod = {
  nameUz: 'bus Multi-lateral Developer',
  nameRu: 'emulation',
  startDate: dayjs('2022-12-01T14:59'),
  endDate: dayjs('2022-12-01T19:28'),
  status: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
