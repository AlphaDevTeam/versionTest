import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PurchaseOrderDetails } from './purchase-order-details.model';
import { PurchaseOrderDetailsService } from './purchase-order-details.service';

@Component({
    selector: 'jhi-purchase-order-details-detail',
    templateUrl: './purchase-order-details-detail.component.html'
})
export class PurchaseOrderDetailsDetailComponent implements OnInit, OnDestroy {

    purchaseOrderDetails: PurchaseOrderDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private purchaseOrderDetailsService: PurchaseOrderDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPurchaseOrderDetails();
    }

    load(id) {
        this.purchaseOrderDetailsService.find(id).subscribe((purchaseOrderDetails) => {
            this.purchaseOrderDetails = purchaseOrderDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPurchaseOrderDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'purchaseOrderDetailsListModification',
            (response) => this.load(this.purchaseOrderDetails.id)
        );
    }
}
