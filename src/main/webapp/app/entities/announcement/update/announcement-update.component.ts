import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AnnouncementFormService, AnnouncementFormGroup } from './announcement-form.service';
import { IAnnouncement } from '../announcement.model';
import { AnnouncementService } from '../service/announcement.service';

@Component({
  selector: 'jhi-announcement-update',
  templateUrl: './announcement-update.component.html',
})
export class AnnouncementUpdateComponent implements OnInit {
  isSaving = false;
  announcement: IAnnouncement | null = null;

  editForm: AnnouncementFormGroup = this.announcementFormService.createAnnouncementFormGroup();

  constructor(
    protected announcementService: AnnouncementService,
    protected announcementFormService: AnnouncementFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ announcement }) => {
      this.announcement = announcement;
      if (announcement) {
        this.updateForm(announcement);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const announcement = this.announcementFormService.getAnnouncement(this.editForm);
    if (announcement.id !== null) {
      this.subscribeToSaveResponse(this.announcementService.update(announcement));
    } else {
      this.subscribeToSaveResponse(this.announcementService.create(announcement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnouncement>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(announcement: IAnnouncement): void {
    this.announcement = announcement;
    this.announcementFormService.resetForm(this.editForm, announcement);
  }
}
