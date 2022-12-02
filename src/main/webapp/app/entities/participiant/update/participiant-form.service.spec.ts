import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../participiant.test-samples';

import { ParticipiantFormService } from './participiant-form.service';

describe('Participiant Form Service', () => {
  let service: ParticipiantFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParticipiantFormService);
  });

  describe('Service methods', () => {
    describe('createParticipiantFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParticipiantFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleUz: expect.any(Object),
            titleRu: expect.any(Object),
            contentUz: expect.any(Object),
            contentRu: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing IParticipiant should create a new form with FormGroup', () => {
        const formGroup = service.createParticipiantFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleUz: expect.any(Object),
            titleRu: expect.any(Object),
            contentUz: expect.any(Object),
            contentRu: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getParticipiant', () => {
      it('should return NewParticipiant for default Participiant initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createParticipiantFormGroup(sampleWithNewData);

        const participiant = service.getParticipiant(formGroup) as any;

        expect(participiant).toMatchObject(sampleWithNewData);
      });

      it('should return NewParticipiant for empty Participiant initial value', () => {
        const formGroup = service.createParticipiantFormGroup();

        const participiant = service.getParticipiant(formGroup) as any;

        expect(participiant).toMatchObject({});
      });

      it('should return IParticipiant', () => {
        const formGroup = service.createParticipiantFormGroup(sampleWithRequiredData);

        const participiant = service.getParticipiant(formGroup) as any;

        expect(participiant).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IParticipiant should not enable id FormControl', () => {
        const formGroup = service.createParticipiantFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewParticipiant should disable id FormControl', () => {
        const formGroup = service.createParticipiantFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
