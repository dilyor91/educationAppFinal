import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IParticipiant } from '../participiant.model';
import { ParticipiantService } from '../service/participiant.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './participiant-delete-dialog.component.html',
})
export class ParticipiantDeleteDialogComponent {
  participiant?: IParticipiant;

  constructor(protected participiantService: ParticipiantService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.participiantService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
