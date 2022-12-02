import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGroups, NewGroups } from '../groups.model';

export type PartialUpdateGroups = Partial<IGroups> & Pick<IGroups, 'id'>;

type RestOf<T extends IGroups | NewGroups> = Omit<T, 'startHour' | 'endHour'> & {
  startHour?: string | null;
  endHour?: string | null;
};

export type RestGroups = RestOf<IGroups>;

export type NewRestGroups = RestOf<NewGroups>;

export type PartialUpdateRestGroups = RestOf<PartialUpdateGroups>;

export type EntityResponseType = HttpResponse<IGroups>;
export type EntityArrayResponseType = HttpResponse<IGroups[]>;

@Injectable({ providedIn: 'root' })
export class GroupsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(groups: NewGroups): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(groups);
    return this.http
      .post<RestGroups>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(groups: IGroups): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(groups);
    return this.http
      .put<RestGroups>(`${this.resourceUrl}/${this.getGroupsIdentifier(groups)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(groups: PartialUpdateGroups): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(groups);
    return this.http
      .patch<RestGroups>(`${this.resourceUrl}/${this.getGroupsIdentifier(groups)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestGroups>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGroups[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGroupsIdentifier(groups: Pick<IGroups, 'id'>): number {
    return groups.id;
  }

  compareGroups(o1: Pick<IGroups, 'id'> | null, o2: Pick<IGroups, 'id'> | null): boolean {
    return o1 && o2 ? this.getGroupsIdentifier(o1) === this.getGroupsIdentifier(o2) : o1 === o2;
  }

  addGroupsToCollectionIfMissing<Type extends Pick<IGroups, 'id'>>(
    groupsCollection: Type[],
    ...groupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const groups: Type[] = groupsToCheck.filter(isPresent);
    if (groups.length > 0) {
      const groupsCollectionIdentifiers = groupsCollection.map(groupsItem => this.getGroupsIdentifier(groupsItem)!);
      const groupsToAdd = groups.filter(groupsItem => {
        const groupsIdentifier = this.getGroupsIdentifier(groupsItem);
        if (groupsCollectionIdentifiers.includes(groupsIdentifier)) {
          return false;
        }
        groupsCollectionIdentifiers.push(groupsIdentifier);
        return true;
      });
      return [...groupsToAdd, ...groupsCollection];
    }
    return groupsCollection;
  }

  protected convertDateFromClient<T extends IGroups | NewGroups | PartialUpdateGroups>(groups: T): RestOf<T> {
    return {
      ...groups,
      startHour: groups.startHour?.toJSON() ?? null,
      endHour: groups.endHour?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restGroups: RestGroups): IGroups {
    return {
      ...restGroups,
      startHour: restGroups.startHour ? dayjs(restGroups.startHour) : undefined,
      endHour: restGroups.endHour ? dayjs(restGroups.endHour) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestGroups>): HttpResponse<IGroups> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestGroups[]>): HttpResponse<IGroups[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
