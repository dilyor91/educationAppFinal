import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IParticipiant, NewParticipiant } from '../participiant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParticipiant for edit and NewParticipiantFormGroupInput for create.
 */
type ParticipiantFormGroupInput = IParticipiant | PartialWithRequiredKeyOf<NewParticipiant>;

type ParticipiantFormDefaults = Pick<NewParticipiant, 'id' | 'status'>;

type ParticipiantFormGroupContent = {
  id: FormControl<IParticipiant['id'] | NewParticipiant['id']>;
  titleUz: FormControl<IParticipiant['titleUz']>;
  titleRu: FormControl<IParticipiant['titleRu']>;
  contentUz: FormControl<IParticipiant['contentUz']>;
  contentRu: FormControl<IParticipiant['contentRu']>;
  status: FormControl<IParticipiant['status']>;
};

export type ParticipiantFormGroup = FormGroup<ParticipiantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParticipiantFormService {
  createParticipiantFormGroup(participiant: ParticipiantFormGroupInput = { id: null }): ParticipiantFormGroup {
    const participiantRawValue = {
      ...this.getFormDefaults(),
      ...participiant,
    };
    return new FormGroup<ParticipiantFormGroupContent>({
      id: new FormControl(
        { value: participiantRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titleUz: new FormControl(participiantRawValue.titleUz),
      titleRu: new FormControl(participiantRawValue.titleRu),
      contentUz: new FormControl(participiantRawValue.contentUz, {
        validators: [Validators.required],
      }),
      contentRu: new FormControl(participiantRawValue.contentRu, {
        validators: [Validators.required],
      }),
      status: new FormControl(participiantRawValue.status, {
        validators: [Validators.required],
      }),
    });
  }

  getParticipiant(form: ParticipiantFormGroup): IParticipiant | NewParticipiant {
    return form.getRawValue() as IParticipiant | NewParticipiant;
  }

  resetForm(form: ParticipiantFormGroup, participiant: ParticipiantFormGroupInput): void {
    const participiantRawValue = { ...this.getFormDefaults(), ...participiant };
    form.reset(
      {
        ...participiantRawValue,
        id: { value: participiantRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ParticipiantFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
