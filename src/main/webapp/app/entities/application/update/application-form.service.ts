import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IApplication, NewApplication } from '../application.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApplication for edit and NewApplicationFormGroupInput for create.
 */
type ApplicationFormGroupInput = IApplication | PartialWithRequiredKeyOf<NewApplication>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IApplication | NewApplication> = Omit<T, 'birthday' | 'certificateGivenDate'> & {
  birthday?: string | null;
  certificateGivenDate?: string | null;
};

type ApplicationFormRawValue = FormValueOf<IApplication>;

type NewApplicationFormRawValue = FormValueOf<NewApplication>;

type ApplicationFormDefaults = Pick<NewApplication, 'id' | 'birthday' | 'certificateGivenDate'>;

type ApplicationFormGroupContent = {
  id: FormControl<ApplicationFormRawValue['id'] | NewApplication['id']>;
  firstName: FormControl<ApplicationFormRawValue['firstName']>;
  lastName: FormControl<ApplicationFormRawValue['lastName']>;
  middleName: FormControl<ApplicationFormRawValue['middleName']>;
  birthday: FormControl<ApplicationFormRawValue['birthday']>;
  gender: FormControl<ApplicationFormRawValue['gender']>;
  passportNo: FormControl<ApplicationFormRawValue['passportNo']>;
  nationality: FormControl<ApplicationFormRawValue['nationality']>;
  occupation: FormControl<ApplicationFormRawValue['occupation']>;
  address: FormControl<ApplicationFormRawValue['address']>;
  mobPhone: FormControl<ApplicationFormRawValue['mobPhone']>;
  homePhone: FormControl<ApplicationFormRawValue['homePhone']>;
  notificationMethod: FormControl<ApplicationFormRawValue['notificationMethod']>;
  certificateNo: FormControl<ApplicationFormRawValue['certificateNo']>;
  certificateGivenDate: FormControl<ApplicationFormRawValue['certificateGivenDate']>;
  status: FormControl<ApplicationFormRawValue['status']>;
  course: FormControl<ApplicationFormRawValue['course']>;
  groups: FormControl<ApplicationFormRawValue['groups']>;
};

export type ApplicationFormGroup = FormGroup<ApplicationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApplicationFormService {
  createApplicationFormGroup(application: ApplicationFormGroupInput = { id: null }): ApplicationFormGroup {
    const applicationRawValue = this.convertApplicationToApplicationRawValue({
      ...this.getFormDefaults(),
      ...application,
    });
    return new FormGroup<ApplicationFormGroupContent>({
      id: new FormControl(
        { value: applicationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      firstName: new FormControl(applicationRawValue.firstName, {
        validators: [Validators.required],
      }),
      lastName: new FormControl(applicationRawValue.lastName, {
        validators: [Validators.required],
      }),
      middleName: new FormControl(applicationRawValue.middleName, {
        validators: [Validators.required],
      }),
      birthday: new FormControl(applicationRawValue.birthday, {
        validators: [Validators.required],
      }),
      gender: new FormControl(applicationRawValue.gender, {
        validators: [Validators.required],
      }),
      passportNo: new FormControl(applicationRawValue.passportNo, {
        validators: [Validators.required],
      }),
      nationality: new FormControl(applicationRawValue.nationality, {
        validators: [Validators.required],
      }),
      occupation: new FormControl(applicationRawValue.occupation, {
        validators: [Validators.required],
      }),
      address: new FormControl(applicationRawValue.address, {
        validators: [Validators.required],
      }),
      mobPhone: new FormControl(applicationRawValue.mobPhone, {
        validators: [Validators.required],
      }),
      homePhone: new FormControl(applicationRawValue.homePhone),
      notificationMethod: new FormControl(applicationRawValue.notificationMethod),
      certificateNo: new FormControl(applicationRawValue.certificateNo),
      certificateGivenDate: new FormControl(applicationRawValue.certificateGivenDate),
      status: new FormControl(applicationRawValue.status),
      course: new FormControl(applicationRawValue.course),
      groups: new FormControl(applicationRawValue.groups),
    });
  }

  getApplication(form: ApplicationFormGroup): IApplication | NewApplication {
    return this.convertApplicationRawValueToApplication(form.getRawValue() as ApplicationFormRawValue | NewApplicationFormRawValue);
  }

  resetForm(form: ApplicationFormGroup, application: ApplicationFormGroupInput): void {
    const applicationRawValue = this.convertApplicationToApplicationRawValue({ ...this.getFormDefaults(), ...application });
    form.reset(
      {
        ...applicationRawValue,
        id: { value: applicationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ApplicationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      birthday: currentTime,
      certificateGivenDate: currentTime,
    };
  }

  private convertApplicationRawValueToApplication(
    rawApplication: ApplicationFormRawValue | NewApplicationFormRawValue
  ): IApplication | NewApplication {
    return {
      ...rawApplication,
      birthday: dayjs(rawApplication.birthday, DATE_TIME_FORMAT),
      certificateGivenDate: dayjs(rawApplication.certificateGivenDate, DATE_TIME_FORMAT),
    };
  }

  private convertApplicationToApplicationRawValue(
    application: IApplication | (Partial<NewApplication> & ApplicationFormDefaults)
  ): ApplicationFormRawValue | PartialWithRequiredKeyOf<NewApplicationFormRawValue> {
    return {
      ...application,
      birthday: application.birthday ? application.birthday.format(DATE_TIME_FORMAT) : undefined,
      certificateGivenDate: application.certificateGivenDate ? application.certificateGivenDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
