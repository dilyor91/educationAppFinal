import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ParticipiantComponent } from '../list/participiant.component';
import { ParticipiantDetailComponent } from '../detail/participiant-detail.component';
import { ParticipiantUpdateComponent } from '../update/participiant-update.component';
import { ParticipiantRoutingResolveService } from './participiant-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const participiantRoute: Routes = [
  {
    path: '',
    component: ParticipiantComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParticipiantDetailComponent,
    resolve: {
      participiant: ParticipiantRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParticipiantUpdateComponent,
    resolve: {
      participiant: ParticipiantRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParticipiantUpdateComponent,
    resolve: {
      participiant: ParticipiantRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(participiantRoute)],
  exports: [RouterModule],
})
export class ParticipiantRoutingModule {}
