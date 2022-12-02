import { IApplication } from 'app/entities/application/application.model';
import { PhotoTypeEnum } from 'app/entities/enumerations/photo-type-enum.model';

export interface IAttachment {
  id: number;
  originalFileName?: string | null;
  fileName?: string | null;
  path?: string | null;
  photoType?: PhotoTypeEnum | null;
  application?: Pick<IApplication, 'id'> | null;
}

export type NewAttachment = Omit<IAttachment, 'id'> & { id: null };
