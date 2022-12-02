import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AnnouncementService } from '../service/announcement.service';

import { AnnouncementComponent } from './announcement.component';

describe('Announcement Management Component', () => {
  let comp: AnnouncementComponent;
  let fixture: ComponentFixture<AnnouncementComponent>;
  let service: AnnouncementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'announcement', component: AnnouncementComponent }]), HttpClientTestingModule],
      declarations: [AnnouncementComponent],
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
      .overrideTemplate(AnnouncementComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnnouncementComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnnouncementService);

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
    expect(comp.announcements?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to announcementService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAnnouncementIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAnnouncementIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
