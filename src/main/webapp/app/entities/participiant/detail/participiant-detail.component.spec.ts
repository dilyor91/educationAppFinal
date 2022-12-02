import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParticipiantDetailComponent } from './participiant-detail.component';

describe('Participiant Management Detail Component', () => {
  let comp: ParticipiantDetailComponent;
  let fixture: ComponentFixture<ParticipiantDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParticipiantDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ participiant: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ParticipiantDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ParticipiantDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load participiant on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.participiant).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
