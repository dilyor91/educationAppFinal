import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ParticipiantFormService } from './participiant-form.service';
import { ParticipiantService } from '../service/participiant.service';
import { IParticipiant } from '../participiant.model';

import { ParticipiantUpdateComponent } from './participiant-update.component';

describe('Participiant Management Update Component', () => {
  let comp: ParticipiantUpdateComponent;
  let fixture: ComponentFixture<ParticipiantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let participiantFormService: ParticipiantFormService;
  let participiantService: ParticipiantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ParticipiantUpdateComponent],
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
      .overrideTemplate(ParticipiantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParticipiantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    participiantFormService = TestBed.inject(ParticipiantFormService);
    participiantService = TestBed.inject(ParticipiantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const participiant: IParticipiant = { id: 456 };

      activatedRoute.data = of({ participiant });
      comp.ngOnInit();

      expect(comp.participiant).toEqual(participiant);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParticipiant>>();
      const participiant = { id: 123 };
      jest.spyOn(participiantFormService, 'getParticipiant').mockReturnValue(participiant);
      jest.spyOn(participiantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participiant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: participiant }));
      saveSubject.complete();

      // THEN
      expect(participiantFormService.getParticipiant).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(participiantService.update).toHaveBeenCalledWith(expect.objectContaining(participiant));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParticipiant>>();
      const participiant = { id: 123 };
      jest.spyOn(participiantFormService, 'getParticipiant').mockReturnValue({ id: null });
      jest.spyOn(participiantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participiant: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: participiant }));
      saveSubject.complete();

      // THEN
      expect(participiantFormService.getParticipiant).toHaveBeenCalled();
      expect(participiantService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParticipiant>>();
      const participiant = { id: 123 };
      jest.spyOn(participiantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participiant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(participiantService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
