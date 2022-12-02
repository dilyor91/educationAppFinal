import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGroups, NewGroups } from '../groups.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGroups for edit and NewGroupsFormGroupInput for create.
 */
type GroupsFormGroupInput = IGroups | PartialWithRequiredKeyOf<NewGroups>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGroups | NewGroups> = Omit<T, 'startHour' | 'endHour'> & {
  startHour?: string | null;
  endHour?: string | null;
};

type GroupsFormRawValue = FormValueOf<IGroups>;

type NewGroupsFormRawValue = FormValueOf<NewGroups>;

type GroupsFormDefaults = Pick<NewGroups, 'id' | 'startHour' | 'endHour' | 'status' | 'full'>;

type GroupsFormGroupContent = {
  id: FormControl<GroupsFormRawValue['id'] | NewGroups['id']>;
  groupNo: FormControl<GroupsFormRawValue['groupNo']>;
  startHour: FormControl<GroupsFormRawValue['startHour']>;
  endHour: FormControl<GroupsFormRawValue['endHour']>;
  capacity: FormControl<GroupsFormRawValue['capacity']>;
  reservedPlace: FormControl<GroupsFormRawValue['reservedPlace']>;
  status: FormControl<GroupsFormRawValue['status']>;
  full: FormControl<GroupsFormRawValue['full']>;
  user: FormControl<GroupsFormRawValue['user']>;
  period: FormControl<GroupsFormRawValue['period']>;
  course: FormControl<GroupsFormRawValue['course']>;
};

export type GroupsFormGroup = FormGroup<GroupsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GroupsFormService {
  createGroupsFormGroup(groups: GroupsFormGroupInput = { id: null }): GroupsFormGroup {
    const groupsRawValue = this.convertGroupsToGroupsRawValue({
      ...this.getFormDefaults(),
      ...groups,
    });
    return new FormGroup<GroupsFormGroupContent>({
      id: new FormControl(
        { value: groupsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      groupNo: new FormControl(groupsRawValue.groupNo, {
        validators: [Validators.required],
      }),
      startHour: new FormControl(groupsRawValue.startHour, {
        validators: [Validators.required],
      }),
      endHour: new FormControl(groupsRawValue.endHour, {
        validators: [Validators.required],
      }),
      capacity: new FormControl(groupsRawValue.capacity, {
        validators: [Validators.required],
      }),
      reservedPlace: new FormControl(groupsRawValue.reservedPlace, {
        validators: [Validators.required],
      }),
      status: new FormControl(groupsRawValue.status, {
        validators: [Validators.required],
      }),
      full: new FormControl(groupsRawValue.full),
      user: new FormControl(groupsRawValue.user),
      period: new FormControl(groupsRawValue.period),
      course: new FormControl(groupsRawValue.course),
    });
  }

  getGroups(form: GroupsFormGroup): IGroups | NewGroups {
    return this.convertGroupsRawValueToGroups(form.getRawValue() as GroupsFormRawValue | NewGroupsFormRawValue);
  }

  resetForm(form: GroupsFormGroup, groups: GroupsFormGroupInput): void {
    const groupsRawValue = this.convertGroupsToGroupsRawValue({ ...this.getFormDefaults(), ...groups });
    form.reset(
      {
        ...groupsRawValue,
        id: { value: groupsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GroupsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startHour: currentTime,
      endHour: currentTime,
      status: false,
      full: false,
    };
  }

  private convertGroupsRawValueToGroups(rawGroups: GroupsFormRawValue | NewGroupsFormRawValue): IGroups | NewGroups {
    return {
      ...rawGroups,
      startHour: dayjs(rawGroups.startHour, DATE_TIME_FORMAT),
      endHour: dayjs(rawGroups.endHour, DATE_TIME_FORMAT),
    };
  }

  private convertGroupsToGroupsRawValue(
    groups: IGroups | (Partial<NewGroups> & GroupsFormDefaults)
  ): GroupsFormRawValue | PartialWithRequiredKeyOf<NewGroupsFormRawValue> {
    return {
      ...groups,
      startHour: groups.startHour ? groups.startHour.format(DATE_TIME_FORMAT) : undefined,
      endHour: groups.endHour ? groups.endHour.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
