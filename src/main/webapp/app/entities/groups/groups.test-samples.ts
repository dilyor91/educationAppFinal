import dayjs from 'dayjs/esm';

import { IGroups, NewGroups } from './groups.model';

export const sampleWithRequiredData: IGroups = {
  id: 3292,
  groupNo: 'modular Fish wireless',
  startHour: dayjs('2022-12-01T13:37'),
  endHour: dayjs('2022-12-01T18:16'),
  capacity: 85345,
  reservedPlace: 10266,
  status: true,
};

export const sampleWithPartialData: IGroups = {
  id: 1490,
  groupNo: 'Orchestrator Steel index',
  startHour: dayjs('2022-12-02T00:43'),
  endHour: dayjs('2022-12-01T22:01'),
  capacity: 98544,
  reservedPlace: 2319,
  status: false,
};

export const sampleWithFullData: IGroups = {
  id: 62164,
  groupNo: 'deposit Communications',
  startHour: dayjs('2022-12-02T08:17'),
  endHour: dayjs('2022-12-02T04:48'),
  capacity: 47323,
  reservedPlace: 94347,
  status: true,
  full: true,
};

export const sampleWithNewData: NewGroups = {
  groupNo: 'Representative alarm',
  startHour: dayjs('2022-12-01T12:26'),
  endHour: dayjs('2022-12-01T12:28'),
  capacity: 20107,
  reservedPlace: 58786,
  status: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
