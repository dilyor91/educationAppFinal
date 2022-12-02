import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParticipiant, NewParticipiant } from '../participiant.model';

export type PartialUpdateParticipiant = Partial<IParticipiant> & Pick<IParticipiant, 'id'>;

export type EntityResponseType = HttpResponse<IParticipiant>;
export type EntityArrayResponseType = HttpResponse<IParticipiant[]>;

@Injectable({ providedIn: 'root' })
export class ParticipiantService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/participiants');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(participiant: NewParticipiant): Observable<EntityResponseType> {
    return this.http.post<IParticipiant>(this.resourceUrl, participiant, { observe: 'response' });
  }

  update(participiant: IParticipiant): Observable<EntityResponseType> {
    return this.http.put<IParticipiant>(`${this.resourceUrl}/${this.getParticipiantIdentifier(participiant)}`, participiant, {
      observe: 'response',
    });
  }

  partialUpdate(participiant: PartialUpdateParticipiant): Observable<EntityResponseType> {
    return this.http.patch<IParticipiant>(`${this.resourceUrl}/${this.getParticipiantIdentifier(participiant)}`, participiant, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParticipiant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParticipiant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getParticipiantIdentifier(participiant: Pick<IParticipiant, 'id'>): number {
    return participiant.id;
  }

  compareParticipiant(o1: Pick<IParticipiant, 'id'> | null, o2: Pick<IParticipiant, 'id'> | null): boolean {
    return o1 && o2 ? this.getParticipiantIdentifier(o1) === this.getParticipiantIdentifier(o2) : o1 === o2;
  }

  addParticipiantToCollectionIfMissing<Type extends Pick<IParticipiant, 'id'>>(
    participiantCollection: Type[],
    ...participiantsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const participiants: Type[] = participiantsToCheck.filter(isPresent);
    if (participiants.length > 0) {
      const participiantCollectionIdentifiers = participiantCollection.map(
        participiantItem => this.getParticipiantIdentifier(participiantItem)!
      );
      const participiantsToAdd = participiants.filter(participiantItem => {
        const participiantIdentifier = this.getParticipiantIdentifier(participiantItem);
        if (participiantCollectionIdentifiers.includes(participiantIdentifier)) {
          return false;
        }
        participiantCollectionIdentifiers.push(participiantIdentifier);
        return true;
      });
      return [...participiantsToAdd, ...participiantCollection];
    }
    return participiantCollection;
  }
}
