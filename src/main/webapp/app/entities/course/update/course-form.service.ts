import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICourse, NewCourse } from '../course.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICourse for edit and NewCourseFormGroupInput for create.
 */
type CourseFormGroupInput = ICourse | PartialWithRequiredKeyOf<NewCourse>;

type CourseFormDefaults = Pick<NewCourse, 'id' | 'firstCourse' | 'status'>;

type CourseFormGroupContent = {
  id: FormControl<ICourse['id'] | NewCourse['id']>;
  nameUz: FormControl<ICourse['nameUz']>;
  nameRu: FormControl<ICourse['nameRu']>;
  subNameUz: FormControl<ICourse['subNameUz']>;
  subNameRu: FormControl<ICourse['subNameRu']>;
  firstCourse: FormControl<ICourse['firstCourse']>;
  status: FormControl<ICourse['status']>;
};

export type CourseFormGroup = FormGroup<CourseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CourseFormService {
  createCourseFormGroup(course: CourseFormGroupInput = { id: null }): CourseFormGroup {
    const courseRawValue = {
      ...this.getFormDefaults(),
      ...course,
    };
    return new FormGroup<CourseFormGroupContent>({
      id: new FormControl(
        { value: courseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nameUz: new FormControl(courseRawValue.nameUz, {
        validators: [Validators.required],
      }),
      nameRu: new FormControl(courseRawValue.nameRu, {
        validators: [Validators.required],
      }),
      subNameUz: new FormControl(courseRawValue.subNameUz, {
        validators: [Validators.required],
      }),
      subNameRu: new FormControl(courseRawValue.subNameRu, {
        validators: [Validators.required],
      }),
      firstCourse: new FormControl(courseRawValue.firstCourse, {
        validators: [Validators.required],
      }),
      status: new FormControl(courseRawValue.status, {
        validators: [Validators.required],
      }),
    });
  }

  getCourse(form: CourseFormGroup): ICourse | NewCourse {
    return form.getRawValue() as ICourse | NewCourse;
  }

  resetForm(form: CourseFormGroup, course: CourseFormGroupInput): void {
    const courseRawValue = { ...this.getFormDefaults(), ...course };
    form.reset(
      {
        ...courseRawValue,
        id: { value: courseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CourseFormDefaults {
    return {
      id: null,
      firstCourse: false,
      status: false,
    };
  }
}
