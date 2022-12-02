import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParticipiant } from '../participiant.model';
import { ParticipiantService } from '../service/participiant.service';

@Injectable({ providedIn: 'root' })
export class ParticipiantRoutingResolveService implements Resolve<IParticipiant | null> {
  constructor(protected service: ParticipiantService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParticipiant | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((participiant: HttpResponse<IParticipiant>) => {
          if (participiant.body) {
            return of(participiant.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
