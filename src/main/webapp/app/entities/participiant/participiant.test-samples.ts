import { IParticipiant, NewParticipiant } from './participiant.model';

export const sampleWithRequiredData: IParticipiant = {
  id: 90284,
  contentUz: 'Plastic',
  contentRu: 'benchmark',
  status: false,
};

export const sampleWithPartialData: IParticipiant = {
  id: 22323,
  titleRu: 'Distributed client-driven',
  contentUz: 'Berkshire',
  contentRu: 'Tools',
  status: true,
};

export const sampleWithFullData: IParticipiant = {
  id: 82118,
  titleUz: 'transmitter reinvent',
  titleRu: 'Re-engineered incubate Bike',
  contentUz: 'feed',
  contentRu: 'SAS',
  status: true,
};

export const sampleWithNewData: NewParticipiant = {
  contentUz: 'Plastic',
  contentRu: 'THX',
  status: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
