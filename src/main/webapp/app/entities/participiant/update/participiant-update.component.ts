import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ParticipiantFormService, ParticipiantFormGroup } from './participiant-form.service';
import { IParticipiant } from '../participiant.model';
import { ParticipiantService } from '../service/participiant.service';

@Component({
  selector: 'jhi-participiant-update',
  templateUrl: './participiant-update.component.html',
})
export class ParticipiantUpdateComponent implements OnInit {
  isSaving = false;
  participiant: IParticipiant | null = null;

  editForm: ParticipiantFormGroup = this.participiantFormService.createParticipiantFormGroup();

  constructor(
    protected participiantService: ParticipiantService,
    protected participiantFormService: ParticipiantFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participiant }) => {
      this.participiant = participiant;
      if (participiant) {
        this.updateForm(participiant);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const participiant = this.participiantFormService.getParticipiant(this.editForm);
    if (participiant.id !== null) {
      this.subscribeToSaveResponse(this.participiantService.update(participiant));
    } else {
      this.subscribeToSaveResponse(this.participiantService.create(participiant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipiant>>): void {
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

  protected updateForm(participiant: IParticipiant): void {
    this.participiant = participiant;
    this.participiantFormService.resetForm(this.editForm, participiant);
  }
}
