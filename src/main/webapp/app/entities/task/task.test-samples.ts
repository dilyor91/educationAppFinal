import { ITask, NewTask } from './task.model';

export const sampleWithRequiredData: ITask = {
  id: 37978,
  contentUz: 'CSS Account Up-sized',
  contentRu: 'software',
  status: true,
};

export const sampleWithPartialData: ITask = {
  id: 13059,
  titleUz: 'structure back-end',
  titleRu: 'Planner programming virtual',
  contentUz: 'Industrial Dirham alarm',
  contentRu: 'Concrete program',
  status: false,
};

export const sampleWithFullData: ITask = {
  id: 56306,
  titleUz: 'bluetooth Engineer Designer',
  titleRu: 'up',
  contentUz: 'Account',
  contentRu: 'Sleek',
  status: false,
};

export const sampleWithNewData: NewTask = {
  contentUz: 'array',
  contentRu: 'Small Account',
  status: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
