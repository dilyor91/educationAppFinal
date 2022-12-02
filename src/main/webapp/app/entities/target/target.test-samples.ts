import { ITarget, NewTarget } from './target.model';

export const sampleWithRequiredData: ITarget = {
  id: 74001,
  contentUz: 'Practical',
  contentRu: 'salmon',
  status: false,
};

export const sampleWithPartialData: ITarget = {
  id: 99381,
  titleUz: 'programming haptic connecting',
  titleRu: 'Unbranded',
  contentUz: 'synergy Ball',
  contentRu: 'Programmable Account',
  status: true,
};

export const sampleWithFullData: ITarget = {
  id: 79577,
  titleUz: 'application quantifying Shoals',
  titleRu: 'generating navigating SAS',
  contentUz: 'invoice Account',
  contentRu: 'Bedfordshire Tasty',
  status: false,
};

export const sampleWithNewData: NewTarget = {
  contentUz: 'Unbranded',
  contentRu: 'B2C',
  status: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
