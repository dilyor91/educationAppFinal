import { ICourse, NewCourse } from './course.model';

export const sampleWithRequiredData: ICourse = {
  id: 59109,
  nameUz: 'multi-byte California',
  nameRu: 'Loan Ergonomic Pants',
  subNameUz: 'empower hacking solution',
  subNameRu: 'compress Lake',
  firstCourse: false,
  status: false,
};

export const sampleWithPartialData: ICourse = {
  id: 60572,
  nameUz: 'improvement',
  nameRu: 'transmitting Facilitator real-time',
  subNameUz: 'port Palestinian Pennsylvania',
  subNameRu: 'XSS',
  firstCourse: true,
  status: false,
};

export const sampleWithFullData: ICourse = {
  id: 51947,
  nameUz: 'Bulgarian',
  nameRu: 'Iowa Designer Innovative',
  subNameUz: 'capacitor compress',
  subNameRu: 'Cambridgeshire Investment',
  firstCourse: true,
  status: true,
};

export const sampleWithNewData: NewCourse = {
  nameUz: 'Electronics portal payment',
  nameRu: 'Car',
  subNameUz: 'copying',
  subNameRu: 'microchip Frozen Lempira',
  firstCourse: true,
  status: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
