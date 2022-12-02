import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IParticipiant } from '../participiant.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../participiant.test-samples';

import { ParticipiantService } from './participiant.service';

const requireRestSample: IParticipiant = {
  ...sampleWithRequiredData,
};

describe('Participiant Service', () => {
  let service: ParticipiantService;
  let httpMock: HttpTestingController;
  let expectedResult: IParticipiant | IParticipiant[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ParticipiantService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Participiant', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const participiant = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(participiant).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Participiant', () => {
      const participiant = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(participiant).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Participiant', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Participiant', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Participiant', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addParticipiantToCollectionIfMissing', () => {
      it('should add a Participiant to an empty array', () => {
        const participiant: IParticipiant = sampleWithRequiredData;
        expectedResult = service.addParticipiantToCollectionIfMissing([], participiant);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(participiant);
      });

      it('should not add a Participiant to an array that contains it', () => {
        const participiant: IParticipiant = sampleWithRequiredData;
        const participiantCollection: IParticipiant[] = [
          {
            ...participiant,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addParticipiantToCollectionIfMissing(participiantCollection, participiant);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Participiant to an array that doesn't contain it", () => {
        const participiant: IParticipiant = sampleWithRequiredData;
        const participiantCollection: IParticipiant[] = [sampleWithPartialData];
        expectedResult = service.addParticipiantToCollectionIfMissing(participiantCollection, participiant);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(participiant);
      });

      it('should add only unique Participiant to an array', () => {
        const participiantArray: IParticipiant[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const participiantCollection: IParticipiant[] = [sampleWithRequiredData];
        expectedResult = service.addParticipiantToCollectionIfMissing(participiantCollection, ...participiantArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const participiant: IParticipiant = sampleWithRequiredData;
        const participiant2: IParticipiant = sampleWithPartialData;
        expectedResult = service.addParticipiantToCollectionIfMissing([], participiant, participiant2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(participiant);
        expect(expectedResult).toContain(participiant2);
      });

      it('should accept null and undefined values', () => {
        const participiant: IParticipiant = sampleWithRequiredData;
        expectedResult = service.addParticipiantToCollectionIfMissing([], null, participiant, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(participiant);
      });

      it('should return initial array if no Participiant is added', () => {
        const participiantCollection: IParticipiant[] = [sampleWithRequiredData];
        expectedResult = service.addParticipiantToCollectionIfMissing(participiantCollection, undefined, null);
        expect(expectedResult).toEqual(participiantCollection);
      });
    });

    describe('compareParticipiant', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareParticipiant(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareParticipiant(entity1, entity2);
        const compareResult2 = service.compareParticipiant(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareParticipiant(entity1, entity2);
        const compareResult2 = service.compareParticipiant(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareParticipiant(entity1, entity2);
        const compareResult2 = service.compareParticipiant(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
