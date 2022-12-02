import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../application.test-samples';

import { ApplicationFormService } from './application-form.service';

describe('Application Form Service', () => {
  let service: ApplicationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApplicationFormService);
  });

  describe('Service methods', () => {
    describe('createApplicationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApplicationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            middleName: expect.any(Object),
            birthday: expect.any(Object),
            gender: expect.any(Object),
            passportNo: expect.any(Object),
            nationality: expect.any(Object),
            occupation: expect.any(Object),
            address: expect.any(Object),
            mobPhone: expect.any(Object),
            homePhone: expect.any(Object),
            notificationMethod: expect.any(Object),
            certificateNo: expect.any(Object),
            certificateGivenDate: expect.any(Object),
            status: expect.any(Object),
            course: expect.any(Object),
            groups: expect.any(Object),
          })
        );
      });

      it('passing IApplication should create a new form with FormGroup', () => {
        const formGroup = service.createApplicationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            middleName: expect.any(Object),
            birthday: expect.any(Object),
            gender: expect.any(Object),
            passportNo: expect.any(Object),
            nationality: expect.any(Object),
            occupation: expect.any(Object),
            address: expect.any(Object),
            mobPhone: expect.any(Object),
            homePhone: expect.any(Object),
            notificationMethod: expect.any(Object),
            certificateNo: expect.any(Object),
            certificateGivenDate: expect.any(Object),
            status: expect.any(Object),
            course: expect.any(Object),
            groups: expect.any(Object),
          })
        );
      });
    });

    describe('getApplication', () => {
      it('should return NewApplication for default Application initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createApplicationFormGroup(sampleWithNewData);

        const application = service.getApplication(formGroup) as any;

        expect(application).toMatchObject(sampleWithNewData);
      });

      it('should return NewApplication for empty Application initial value', () => {
        const formGroup = service.createApplicationFormGroup();

        const application = service.getApplication(formGroup) as any;

        expect(application).toMatchObject({});
      });

      it('should return IApplication', () => {
        const formGroup = service.createApplicationFormGroup(sampleWithRequiredData);

        const application = service.getApplication(formGroup) as any;

        expect(application).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApplication should not enable id FormControl', () => {
        const formGroup = service.createApplicationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApplication should disable id FormControl', () => {
        const formGroup = service.createApplicationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
