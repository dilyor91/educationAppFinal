import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GroupsFormService } from './groups-form.service';
import { GroupsService } from '../service/groups.service';
import { IGroups } from '../groups.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPeriod } from 'app/entities/period/period.model';
import { PeriodService } from 'app/entities/period/service/period.service';
import { ICourse } from 'app/entities/course/course.model';
import { CourseService } from 'app/entities/course/service/course.service';

import { GroupsUpdateComponent } from './groups-update.component';

describe('Groups Management Update Component', () => {
  let comp: GroupsUpdateComponent;
  let fixture: ComponentFixture<GroupsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let groupsFormService: GroupsFormService;
  let groupsService: GroupsService;
  let userService: UserService;
  let periodService: PeriodService;
  let courseService: CourseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GroupsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(GroupsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GroupsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    groupsFormService = TestBed.inject(GroupsFormService);
    groupsService = TestBed.inject(GroupsService);
    userService = TestBed.inject(UserService);
    periodService = TestBed.inject(PeriodService);
    courseService = TestBed.inject(CourseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const groups: IGroups = { id: 456 };
      const user: IUser = { id: 71813 };
      groups.user = user;

      const userCollection: IUser[] = [{ id: 52684 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ groups });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Period query and add missing value', () => {
      const groups: IGroups = { id: 456 };
      const period: IPeriod = { id: 18600 };
      groups.period = period;

      const periodCollection: IPeriod[] = [{ id: 63446 }];
      jest.spyOn(periodService, 'query').mockReturnValue(of(new HttpResponse({ body: periodCollection })));
      const additionalPeriods = [period];
      const expectedCollection: IPeriod[] = [...additionalPeriods, ...periodCollection];
      jest.spyOn(periodService, 'addPeriodToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ groups });
      comp.ngOnInit();

      expect(periodService.query).toHaveBeenCalled();
      expect(periodService.addPeriodToCollectionIfMissing).toHaveBeenCalledWith(
        periodCollection,
        ...additionalPeriods.map(expect.objectContaining)
      );
      expect(comp.periodsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Course query and add missing value', () => {
      const groups: IGroups = { id: 456 };
      const course: ICourse = { id: 54644 };
      groups.course = course;

      const courseCollection: ICourse[] = [{ id: 39310 }];
      jest.spyOn(courseService, 'query').mockReturnValue(of(new HttpResponse({ body: courseCollection })));
      const additionalCourses = [course];
      const expectedCollection: ICourse[] = [...additionalCourses, ...courseCollection];
      jest.spyOn(courseService, 'addCourseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ groups });
      comp.ngOnInit();

      expect(courseService.query).toHaveBeenCalled();
      expect(courseService.addCourseToCollectionIfMissing).toHaveBeenCalledWith(
        courseCollection,
        ...additionalCourses.map(expect.objectContaining)
      );
      expect(comp.coursesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const groups: IGroups = { id: 456 };
      const user: IUser = { id: 50661 };
      groups.user = user;
      const period: IPeriod = { id: 81981 };
      groups.period = period;
      const course: ICourse = { id: 66609 };
      groups.course = course;

      activatedRoute.data = of({ groups });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.periodsSharedCollection).toContain(period);
      expect(comp.coursesSharedCollection).toContain(course);
      expect(comp.groups).toEqual(groups);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGroups>>();
      const groups = { id: 123 };
      jest.spyOn(groupsFormService, 'getGroups').mockReturnValue(groups);
      jest.spyOn(groupsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groups });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: groups }));
      saveSubject.complete();

      // THEN
      expect(groupsFormService.getGroups).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(groupsService.update).toHaveBeenCalledWith(expect.objectContaining(groups));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGroups>>();
      const groups = { id: 123 };
      jest.spyOn(groupsFormService, 'getGroups').mockReturnValue({ id: null });
      jest.spyOn(groupsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groups: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: groups }));
      saveSubject.complete();

      // THEN
      expect(groupsFormService.getGroups).toHaveBeenCalled();
      expect(groupsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGroups>>();
      const groups = { id: 123 };
      jest.spyOn(groupsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ groups });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(groupsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePeriod', () => {
      it('Should forward to periodService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(periodService, 'comparePeriod');
        comp.comparePeriod(entity, entity2);
        expect(periodService.comparePeriod).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCourse', () => {
      it('Should forward to courseService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(courseService, 'compareCourse');
        comp.compareCourse(entity, entity2);
        expect(courseService.compareCourse).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
