import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnnouncement } from '../announcement.model';
import { AnnouncementService } from '../service/announcement.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './announcement-delete-dialog.component.html',
})
export class AnnouncementDeleteDialogComponent {
  announcement?: IAnnouncement;

  constructor(protected announcementService: AnnouncementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.announcementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
