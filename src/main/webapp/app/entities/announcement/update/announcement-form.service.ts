import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnnouncement, NewAnnouncement } from '../announcement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnnouncement for edit and NewAnnouncementFormGroupInput for create.
 */
type AnnouncementFormGroupInput = IAnnouncement | PartialWithRequiredKeyOf<NewAnnouncement>;

type AnnouncementFormDefaults = Pick<NewAnnouncement, 'id' | 'status'>;

type AnnouncementFormGroupContent = {
  id: FormControl<IAnnouncement['id'] | NewAnnouncement['id']>;
  titleUz: FormControl<IAnnouncement['titleUz']>;
  titleRu: FormControl<IAnnouncement['titleRu']>;
  contentUz: FormControl<IAnnouncement['contentUz']>;
  contentRu: FormControl<IAnnouncement['contentRu']>;
  status: FormControl<IAnnouncement['status']>;
};

export type AnnouncementFormGroup = FormGroup<AnnouncementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnnouncementFormService {
  createAnnouncementFormGroup(announcement: AnnouncementFormGroupInput = { id: null }): AnnouncementFormGroup {
    const announcementRawValue = {
      ...this.getFormDefaults(),
      ...announcement,
    };
    return new FormGroup<AnnouncementFormGroupContent>({
      id: new FormControl(
        { value: announcementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titleUz: new FormControl(announcementRawValue.titleUz),
      titleRu: new FormControl(announcementRawValue.titleRu),
      contentUz: new FormControl(announcementRawValue.contentUz, {
        validators: [Validators.required],
      }),
      contentRu: new FormControl(announcementRawValue.contentRu, {
        validators: [Validators.required],
      }),
      status: new FormControl(announcementRawValue.status, {
        validators: [Validators.required],
      }),
    });
  }

  getAnnouncement(form: AnnouncementFormGroup): IAnnouncement | NewAnnouncement {
    return form.getRawValue() as IAnnouncement | NewAnnouncement;
  }

  resetForm(form: AnnouncementFormGroup, announcement: AnnouncementFormGroupInput): void {
    const announcementRawValue = { ...this.getFormDefaults(), ...announcement };
    form.reset(
      {
        ...announcementRawValue,
        id: { value: announcementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AnnouncementFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
