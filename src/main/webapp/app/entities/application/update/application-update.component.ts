import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ApplicationFormService, ApplicationFormGroup } from './application-form.service';
import { IApplication } from '../application.model';
import { ApplicationService } from '../service/application.service';
import { ICourse } from 'app/entities/course/course.model';
import { CourseService } from 'app/entities/course/service/course.service';
import { IGroups } from 'app/entities/groups/groups.model';
import { GroupsService } from 'app/entities/groups/service/groups.service';
import { GenderEnum } from 'app/entities/enumerations/gender-enum.model';
import { AppStatusEnum } from 'app/entities/enumerations/app-status-enum.model';

@Component({
  selector: 'jhi-application-update',
  templateUrl: './application-update.component.html',
})
export class ApplicationUpdateComponent implements OnInit {
  isSaving = false;
  application: IApplication | null = null;
  genderEnumValues = Object.keys(GenderEnum);
  appStatusEnumValues = Object.keys(AppStatusEnum);

  coursesSharedCollection: ICourse[] = [];
  groupsSharedCollection: IGroups[] = [];

  editForm: ApplicationFormGroup = this.applicationFormService.createApplicationFormGroup();

  constructor(
    protected applicationService: ApplicationService,
    protected applicationFormService: ApplicationFormService,
    protected courseService: CourseService,
    protected groupsService: GroupsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCourse = (o1: ICourse | null, o2: ICourse | null): boolean => this.courseService.compareCourse(o1, o2);

  compareGroups = (o1: IGroups | null, o2: IGroups | null): boolean => this.groupsService.compareGroups(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ application }) => {
      this.application = application;
      if (application) {
        this.updateForm(application);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const application = this.applicationFormService.getApplication(this.editForm);
    if (application.id !== null) {
      this.subscribeToSaveResponse(this.applicationService.update(application));
    } else {
      this.subscribeToSaveResponse(this.applicationService.create(application));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplication>>): void {
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

  protected updateForm(application: IApplication): void {
    this.application = application;
    this.applicationFormService.resetForm(this.editForm, application);

    this.coursesSharedCollection = this.courseService.addCourseToCollectionIfMissing<ICourse>(
      this.coursesSharedCollection,
      application.course
    );
    this.groupsSharedCollection = this.groupsService.addGroupsToCollectionIfMissing<IGroups>(
      this.groupsSharedCollection,
      application.groups
    );
  }

  protected loadRelationshipsOptions(): void {
    this.courseService
      .query()
      .pipe(map((res: HttpResponse<ICourse[]>) => res.body ?? []))
      .pipe(map((courses: ICourse[]) => this.courseService.addCourseToCollectionIfMissing<ICourse>(courses, this.application?.course)))
      .subscribe((courses: ICourse[]) => (this.coursesSharedCollection = courses));

    this.groupsService
      .query()
      .pipe(map((res: HttpResponse<IGroups[]>) => res.body ?? []))
      .pipe(map((groups: IGroups[]) => this.groupsService.addGroupsToCollectionIfMissing<IGroups>(groups, this.application?.groups)))
      .subscribe((groups: IGroups[]) => (this.groupsSharedCollection = groups));
  }
}
