import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExtraUser } from './extra-user.model';
import { ExtraUserPopupService } from './extra-user-popup.service';
import { ExtraUserService } from './extra-user.service';

@Component({
    selector: 'jhi-extra-user-delete-dialog',
    templateUrl: './extra-user-delete-dialog.component.html'
})
export class ExtraUserDeleteDialogComponent {

    extraUser: ExtraUser;

    constructor(
        private extraUserService: ExtraUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.extraUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'extraUserListModification',
                content: 'Deleted an extraUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-extra-user-delete-popup',
    template: ''
})
export class ExtraUserDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private extraUserPopupService: ExtraUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.extraUserPopupService
                .open(ExtraUserDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
