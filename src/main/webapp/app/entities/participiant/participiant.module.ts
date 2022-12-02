import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ParticipiantComponent } from './list/participiant.component';
import { ParticipiantDetailComponent } from './detail/participiant-detail.component';
import { ParticipiantUpdateComponent } from './update/participiant-update.component';
import { ParticipiantDeleteDialogComponent } from './delete/participiant-delete-dialog.component';
import { ParticipiantRoutingModule } from './route/participiant-routing.module';

@NgModule({
  imports: [SharedModule, ParticipiantRoutingModule],
  declarations: [ParticipiantComponent, ParticipiantDetailComponent, ParticipiantUpdateComponent, ParticipiantDeleteDialogComponent],
})
export class ParticipiantModule {}
