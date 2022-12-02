import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPeriod, NewPeriod } from '../period.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPeriod for edit and NewPeriodFormGroupInput for create.
 */
type PeriodFormGroupInput = IPeriod | PartialWithRequiredKeyOf<NewPeriod>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPeriod | NewPeriod> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

type PeriodFormRawValue = FormValueOf<IPeriod>;

type NewPeriodFormRawValue = FormValueOf<NewPeriod>;

type PeriodFormDefaults = Pick<NewPeriod, 'id' | 'startDate' | 'endDate' | 'status'>;

type PeriodFormGroupContent = {
  id: FormControl<PeriodFormRawValue['id'] | NewPeriod['id']>;
  nameUz: FormControl<PeriodFormRawValue['nameUz']>;
  nameRu: FormControl<PeriodFormRawValue['nameRu']>;
  startDate: FormControl<PeriodFormRawValue['startDate']>;
  endDate: FormControl<PeriodFormRawValue['endDate']>;
  status: FormControl<PeriodFormRawValue['status']>;
};

export type PeriodFormGroup = FormGroup<PeriodFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PeriodFormService {
  createPeriodFormGroup(period: PeriodFormGroupInput = { id: null }): PeriodFormGroup {
    const periodRawValue = this.convertPeriodToPeriodRawValue({
      ...this.getFormDefaults(),
      ...period,
    });
    return new FormGroup<PeriodFormGroupContent>({
      id: new FormControl(
        { value: periodRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nameUz: new FormControl(periodRawValue.nameUz, {
        validators: [Validators.required],
      }),
      nameRu: new FormControl(periodRawValue.nameRu, {
        validators: [Validators.required],
      }),
      startDate: new FormControl(periodRawValue.startDate, {
        validators: [Validators.required],
      }),
      endDate: new FormControl(periodRawValue.endDate, {
        validators: [Validators.required],
      }),
      status: new FormControl(periodRawValue.status, {
        validators: [Validators.required],
      }),
    });
  }

  getPeriod(form: PeriodFormGroup): IPeriod | NewPeriod {
    return this.convertPeriodRawValueToPeriod(form.getRawValue() as PeriodFormRawValue | NewPeriodFormRawValue);
  }

  resetForm(form: PeriodFormGroup, period: PeriodFormGroupInput): void {
    const periodRawValue = this.convertPeriodToPeriodRawValue({ ...this.getFormDefaults(), ...period });
    form.reset(
      {
        ...periodRawValue,
        id: { value: periodRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PeriodFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
      status: false,
    };
  }

  private convertPeriodRawValueToPeriod(rawPeriod: PeriodFormRawValue | NewPeriodFormRawValue): IPeriod | NewPeriod {
    return {
      ...rawPeriod,
      startDate: dayjs(rawPeriod.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawPeriod.endDate, DATE_TIME_FORMAT),
    };
  }

  private convertPeriodToPeriodRawValue(
    period: IPeriod | (Partial<NewPeriod> & PeriodFormDefaults)
  ): PeriodFormRawValue | PartialWithRequiredKeyOf<NewPeriodFormRawValue> {
    return {
      ...period,
      startDate: period.startDate ? period.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: period.endDate ? period.endDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
