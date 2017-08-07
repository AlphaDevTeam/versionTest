import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PurchaseOrderDetails } from './purchase-order-details.model';
import { PurchaseOrderDetailsPopupService } from './purchase-order-details-popup.service';
import { PurchaseOrderDetailsService } from './purchase-order-details.service';

@Component({
    selector: 'jhi-purchase-order-details-delete-dialog',
    templateUrl: './purchase-order-details-delete-dialog.component.html'
})
export class PurchaseOrderDetailsDeleteDialogComponent {

    purchaseOrderDetails: PurchaseOrderDetails;

    constructor(
        private purchaseOrderDetailsService: PurchaseOrderDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.purchaseOrderDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'purchaseOrderDetailsListModification',
                content: 'Deleted an purchaseOrderDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-order-details-delete-popup',
    template: ''
})
export class PurchaseOrderDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private purchaseOrderDetailsPopupService: PurchaseOrderDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.purchaseOrderDetailsPopupService
                .open(PurchaseOrderDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
