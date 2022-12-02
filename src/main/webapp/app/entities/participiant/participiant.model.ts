export interface IParticipiant {
  id: number;
  titleUz?: string | null;
  titleRu?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  status?: boolean | null;
}

export type NewParticipiant = Omit<IParticipiant, 'id'> & { id: null };
