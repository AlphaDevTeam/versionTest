import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VersionTestSharedModule } from '../../shared';
import {PurchaseOrderDetailsComponent} from '../purchase-order-details/purchase-order-details.component';
import {
    PurchaseOrderService,
    PurchaseOrderPopupService,
    PurchaseOrderComponent,
    PurchaseOrderDetailComponent,
    PurchaseOrderDialogComponent,
    PurchaseOrderPopupComponent,
    PurchaseOrderDeletePopupComponent,
    PurchaseOrderDeleteDialogComponent,
    purchaseOrderRoute,
    purchaseOrderPopupRoute,
    PurchaseOrderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...purchaseOrderRoute,
    ...purchaseOrderPopupRoute,
];

@NgModule({
    imports: [
        VersionTestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PurchaseOrderComponent,
        PurchaseOrderDetailComponent,
        PurchaseOrderDialogComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderPopupComponent,
        PurchaseOrderDeletePopupComponent,
        PurchaseOrderDetailsComponent,
    ],
    entryComponents: [
        PurchaseOrderComponent,
        PurchaseOrderDialogComponent,
        PurchaseOrderPopupComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent,
    ],
    providers: [
        PurchaseOrderService,
        PurchaseOrderPopupService,
        PurchaseOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VersionTestPurchaseOrderModule {}
