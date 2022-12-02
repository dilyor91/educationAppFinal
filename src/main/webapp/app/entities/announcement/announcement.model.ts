export interface IAnnouncement {
  id: number;
  titleUz?: string | null;
  titleRu?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  status?: boolean | null;
}

export type NewAnnouncement = Omit<IAnnouncement, 'id'> & { id: null };
