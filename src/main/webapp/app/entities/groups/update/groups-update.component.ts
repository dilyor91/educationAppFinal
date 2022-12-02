import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GroupsFormService, GroupsFormGroup } from './groups-form.service';
import { IGroups } from '../groups.model';
import { GroupsService } from '../service/groups.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPeriod } from 'app/entities/period/period.model';
import { PeriodService } from 'app/entities/period/service/period.service';
import { ICourse } from 'app/entities/course/course.model';
import { CourseService } from 'app/entities/course/service/course.service';

@Component({
  selector: 'jhi-groups-update',
  templateUrl: './groups-update.component.html',
})
export class GroupsUpdateComponent implements OnInit {
  isSaving = false;
  groups: IGroups | null = null;

  usersSharedCollection: IUser[] = [];
  periodsSharedCollection: IPeriod[] = [];
  coursesSharedCollection: ICourse[] = [];

  editForm: GroupsFormGroup = this.groupsFormService.createGroupsFormGroup();

  constructor(
    protected groupsService: GroupsService,
    protected groupsFormService: GroupsFormService,
    protected userService: UserService,
    protected periodService: PeriodService,
    protected courseService: CourseService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  comparePeriod = (o1: IPeriod | null, o2: IPeriod | null): boolean => this.periodService.comparePeriod(o1, o2);

  compareCourse = (o1: ICourse | null, o2: ICourse | null): boolean => this.courseService.compareCourse(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groups }) => {
      this.groups = groups;
      if (groups) {
        this.updateForm(groups);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const groups = this.groupsFormService.getGroups(this.editForm);
    if (groups.id !== null) {
      this.subscribeToSaveResponse(this.groupsService.update(groups));
    } else {
      this.subscribeToSaveResponse(this.groupsService.create(groups));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroups>>): void {
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

  protected updateForm(groups: IGroups): void {
    this.groups = groups;
    this.groupsFormService.resetForm(this.editForm, groups);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, groups.user);
    this.periodsSharedCollection = this.periodService.addPeriodToCollectionIfMissing<IPeriod>(this.periodsSharedCollection, groups.period);
    this.coursesSharedCollection = this.courseService.addCourseToCollectionIfMissing<ICourse>(this.coursesSharedCollection, groups.course);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.groups?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.periodService
      .query()
      .pipe(map((res: HttpResponse<IPeriod[]>) => res.body ?? []))
      .pipe(map((periods: IPeriod[]) => this.periodService.addPeriodToCollectionIfMissing<IPeriod>(periods, this.groups?.period)))
      .subscribe((periods: IPeriod[]) => (this.periodsSharedCollection = periods));

    this.courseService
      .query()
      .pipe(map((res: HttpResponse<ICourse[]>) => res.body ?? []))
      .pipe(map((courses: ICourse[]) => this.courseService.addCourseToCollectionIfMissing<ICourse>(courses, this.groups?.course)))
      .subscribe((courses: ICourse[]) => (this.coursesSharedCollection = courses));
  }
}
