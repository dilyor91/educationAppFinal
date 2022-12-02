import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnnouncement, NewAnnouncement } from '../announcement.model';

export type PartialUpdateAnnouncement = Partial<IAnnouncement> & Pick<IAnnouncement, 'id'>;

export type EntityResponseType = HttpResponse<IAnnouncement>;
export type EntityArrayResponseType = HttpResponse<IAnnouncement[]>;

@Injectable({ providedIn: 'root' })
export class AnnouncementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/announcements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(announcement: NewAnnouncement): Observable<EntityResponseType> {
    return this.http.post<IAnnouncement>(this.resourceUrl, announcement, { observe: 'response' });
  }

  update(announcement: IAnnouncement): Observable<EntityResponseType> {
    return this.http.put<IAnnouncement>(`${this.resourceUrl}/${this.getAnnouncementIdentifier(announcement)}`, announcement, {
      observe: 'response',
    });
  }

  partialUpdate(announcement: PartialUpdateAnnouncement): Observable<EntityResponseType> {
    return this.http.patch<IAnnouncement>(`${this.resourceUrl}/${this.getAnnouncementIdentifier(announcement)}`, announcement, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnnouncement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnnouncement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnnouncementIdentifier(announcement: Pick<IAnnouncement, 'id'>): number {
    return announcement.id;
  }

  compareAnnouncement(o1: Pick<IAnnouncement, 'id'> | null, o2: Pick<IAnnouncement, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnnouncementIdentifier(o1) === this.getAnnouncementIdentifier(o2) : o1 === o2;
  }

  addAnnouncementToCollectionIfMissing<Type extends Pick<IAnnouncement, 'id'>>(
    announcementCollection: Type[],
    ...announcementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const announcements: Type[] = announcementsToCheck.filter(isPresent);
    if (announcements.length > 0) {
      const announcementCollectionIdentifiers = announcementCollection.map(
        announcementItem => this.getAnnouncementIdentifier(announcementItem)!
      );
      const announcementsToAdd = announcements.filter(announcementItem => {
        const announcementIdentifier = this.getAnnouncementIdentifier(announcementItem);
        if (announcementCollectionIdentifiers.includes(announcementIdentifier)) {
          return false;
        }
        announcementCollectionIdentifiers.push(announcementIdentifier);
        return true;
      });
      return [...announcementsToAdd, ...announcementCollection];
    }
    return announcementCollection;
  }
}
