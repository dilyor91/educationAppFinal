import dayjs from 'dayjs/esm';

import { GenderEnum } from 'app/entities/enumerations/gender-enum.model';
import { AppStatusEnum } from 'app/entities/enumerations/app-status-enum.model';

import { IApplication, NewApplication } from './application.model';

export const sampleWithRequiredData: IApplication = {
  id: 99506,
  firstName: 'Aimee',
  lastName: 'Kulas',
  middleName: 'Gloves',
  birthday: dayjs('2022-12-02T04:18'),
  gender: GenderEnum['FEMALE'],
  passportNo: 'Handmade invoice',
  nationality: 'Health sensor',
  occupation: 'Marks value-added',
  address: 'Up-sized Handmade Cotton',
  mobPhone: 'magnetic programming payment',
};

export const sampleWithPartialData: IApplication = {
  id: 581,
  firstName: 'Ruthie',
  lastName: 'Daugherty',
  middleName: 'Coordinator monitor',
  birthday: dayjs('2022-12-02T07:33'),
  gender: GenderEnum['FEMALE'],
  passportNo: 'Handmade Gorgeous 1080p',
  nationality: 'Public-key Wooden Montana',
  occupation: 'sensor Movies database',
  address: 'compressing AGP',
  mobPhone: 'Springs synergies Regional',
  homePhone: 'tan',
  notificationMethod: 'Bedfordshire back-end',
  certificateGivenDate: dayjs('2022-12-02T09:34'),
  status: AppStatusEnum['NEW'],
};

export const sampleWithFullData: IApplication = {
  id: 59675,
  firstName: 'Juliana',
  lastName: 'Weber',
  middleName: 'schemas Togo',
  birthday: dayjs('2022-12-02T04:56'),
  gender: GenderEnum['MALE'],
  passportNo: 'Illinois Mayotte',
  nationality: 'holistic Steel',
  occupation: 'Extended bluetooth Nevada',
  address: 'robust',
  mobPhone: 'copying',
  homePhone: 'Illinois',
  notificationMethod: 'auxiliary XML',
  certificateNo: 'synergize',
  certificateGivenDate: dayjs('2022-12-01T12:56'),
  status: AppStatusEnum['DRAFT'],
};

export const sampleWithNewData: NewApplication = {
  firstName: 'Easter',
  lastName: 'Brekke',
  middleName: 'calculate Tajikistan blockchains',
  birthday: dayjs('2022-12-02T09:48'),
  gender: GenderEnum['FEMALE'],
  passportNo: 'Ergonomic XML needs-based',
  nationality: 'deliver',
  occupation: 'violet',
  address: 'out-of-the-box',
  mobPhone: 'deposit streamline',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
