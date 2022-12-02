import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ParticipiantService } from '../service/participiant.service';

import { ParticipiantComponent } from './participiant.component';

describe('Participiant Management Component', () => {
  let comp: ParticipiantComponent;
  let fixture: ComponentFixture<ParticipiantComponent>;
  let service: ParticipiantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'participiant', component: ParticipiantComponent }]), HttpClientTestingModule],
      declarations: [ParticipiantComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ParticipiantComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParticipiantComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ParticipiantService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.participiants?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to participiantService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getParticipiantIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getParticipiantIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
