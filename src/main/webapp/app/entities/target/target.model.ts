export interface ITarget {
  id: number;
  titleUz?: string | null;
  titleRu?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  status?: boolean | null;
}

export type NewTarget = Omit<ITarget, 'id'> & { id: null };
