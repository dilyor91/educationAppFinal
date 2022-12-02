import { PhotoTypeEnum } from 'app/entities/enumerations/photo-type-enum.model';

import { IAttachment, NewAttachment } from './attachment.model';

export const sampleWithRequiredData: IAttachment = {
  id: 43624,
  originalFileName: 'programming Account',
  fileName: 'Buckinghamshire RSS',
  path: 'Practical Product',
};

export const sampleWithPartialData: IAttachment = {
  id: 95295,
  originalFileName: 'Central dedicated',
  fileName: 'Sausages Object-based',
  path: 'interfaces Reunion haptic',
  photoType: PhotoTypeEnum['MAIN_PAGE'],
};

export const sampleWithFullData: IAttachment = {
  id: 31219,
  originalFileName: 'Investor quantifying',
  fileName: 'optical auxiliary viral',
  path: 'Computers CSS',
  photoType: PhotoTypeEnum['CERTIFICATE'],
};

export const sampleWithNewData: NewAttachment = {
  originalFileName: 'Enterprise-wide',
  fileName: 'RSS bluetooth purple',
  path: 'SSL whiteboard',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
