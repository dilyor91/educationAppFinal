export interface ICourse {
  id: number;
  nameUz?: string | null;
  nameRu?: string | null;
  subNameUz?: string | null;
  subNameRu?: string | null;
  firstCourse?: boolean | null;
  status?: boolean | null;
}

export type NewCourse = Omit<ICourse, 'id'> & { id: null };
