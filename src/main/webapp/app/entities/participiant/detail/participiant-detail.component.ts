import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParticipiant } from '../participiant.model';

@Component({
  selector: 'jhi-participiant-detail',
  templateUrl: './participiant-detail.component.html',
})
export class ParticipiantDetailComponent implements OnInit {
  participiant: IParticipiant | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participiant }) => {
      this.participiant = participiant;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
