export interface ITask {
  id: number;
  titleUz?: string | null;
  titleRu?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  status?: boolean | null;
}

export type NewTask = Omit<ITask, 'id'> & { id: null };
