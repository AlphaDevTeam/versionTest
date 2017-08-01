import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExtraUser } from './extra-user.model';
import { ExtraUserPopupService } from './extra-user-popup.service';
import { ExtraUserService } from './extra-user.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-extra-user-dialog',
    templateUrl: './extra-user-dialog.component.html'
})
export class ExtraUserDialogComponent implements OnInit {

    extraUser: ExtraUser;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private extraUserService: ExtraUserService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.extraUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.extraUserService.update(this.extraUser));
        } else {
            this.subscribeToSaveResponse(
                this.extraUserService.create(this.extraUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<ExtraUser>) {
        result.subscribe((res: ExtraUser) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ExtraUser) {
        this.eventManager.broadcast({ name: 'extraUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-extra-user-popup',
    template: ''
})
export class ExtraUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private extraUserPopupService: ExtraUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.extraUserPopupService
                    .open(ExtraUserDialogComponent as Component, params['id']);
            } else {
                this.extraUserPopupService
                    .open(ExtraUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
