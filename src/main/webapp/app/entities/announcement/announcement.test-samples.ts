import { IAnnouncement, NewAnnouncement } from './announcement.model';

export const sampleWithRequiredData: IAnnouncement = {
  id: 76223,
  contentUz: 'solution',
  contentRu: 'JSON',
  status: true,
};

export const sampleWithPartialData: IAnnouncement = {
  id: 99090,
  titleRu: 'Cotton Borders',
  contentUz: 'generating Data COM',
  contentRu: 'bypassing hack magenta',
  status: true,
};

export const sampleWithFullData: IAnnouncement = {
  id: 71126,
  titleUz: 'Concrete',
  titleRu: 'engine RAM Incredible',
  contentUz: 'deposit',
  contentRu: 'black Bike mobile',
  status: true,
};

export const sampleWithNewData: NewAnnouncement = {
  contentUz: 'Cambridgeshire Arkansas',
  contentRu: 'Shirt JSON',
  status: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
