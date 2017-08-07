import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PurchaseOrderDetails } from './purchase-order-details.model';
import { PurchaseOrderDetailsPopupService } from './purchase-order-details-popup.service';
import { PurchaseOrderDetailsService } from './purchase-order-details.service';
import { Company, CompanyService } from '../company';
import { PurchaseOrder, PurchaseOrderService } from '../purchase-order';
import { TestItem, TestItemService } from '../test-item';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-purchase-order-details-dialog',
    templateUrl: './purchase-order-details-dialog.component.html'
})
export class PurchaseOrderDetailsDialogComponent implements OnInit {

    purchaseOrderDetails: PurchaseOrderDetails;
    isSaving: boolean;

    companies: Company[];

    purchaseorders: PurchaseOrder[];

    testitems: TestItem[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private purchaseOrderDetailsService: PurchaseOrderDetailsService,
        private companyService: CompanyService,
        private purchaseOrderService: PurchaseOrderService,
        private testItemService: TestItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.purchaseOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.purchaseorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.testItemService.query()
            .subscribe((res: ResponseWrapper) => { this.testitems = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.purchaseOrderDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.purchaseOrderDetailsService.update(this.purchaseOrderDetails));
        } else {
            this.subscribeToSaveResponse(
                this.purchaseOrderDetailsService.create(this.purchaseOrderDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<PurchaseOrderDetails>) {
        result.subscribe((res: PurchaseOrderDetails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PurchaseOrderDetails) {
        this.eventManager.broadcast({ name: 'purchaseOrderDetailsListModification', content: 'OK'});
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

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackPurchaseOrderById(index: number, item: PurchaseOrder) {
        return item.id;
    }

    trackTestItemById(index: number, item: TestItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-purchase-order-details-popup',
    template: ''
})
export class PurchaseOrderDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private purchaseOrderDetailsPopupService: PurchaseOrderDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.purchaseOrderDetailsPopupService
                    .open(PurchaseOrderDetailsDialogComponent as Component, params['id']);
            } else {
                this.purchaseOrderDetailsPopupService
                    .open(PurchaseOrderDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
