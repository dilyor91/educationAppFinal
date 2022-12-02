import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnnouncement } from '../announcement.model';
import { AnnouncementService } from '../service/announcement.service';

@Injectable({ providedIn: 'root' })
export class AnnouncementRoutingResolveService implements Resolve<IAnnouncement | null> {
  constructor(protected service: AnnouncementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnnouncement | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((announcement: HttpResponse<IAnnouncement>) => {
          if (announcement.body) {
            return of(announcement.body);
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