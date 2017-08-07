import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VersionTestSharedModule } from '../../shared';
import {
    PurchaseOrderDetailsService,
    PurchaseOrderDetailsPopupService,
    PurchaseOrderDetailsComponent,
    PurchaseOrderDetailsDetailComponent,
    PurchaseOrderDetailsDialogComponent,
    PurchaseOrderDetailsPopupComponent,
    PurchaseOrderDetailsDeletePopupComponent,
    PurchaseOrderDetailsDeleteDialogComponent,
    purchaseOrderDetailsRoute,
    purchaseOrderDetailsPopupRoute,
    PurchaseOrderDetailsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...purchaseOrderDetailsRoute,
    ...purchaseOrderDetailsPopupRoute,
];

@NgModule({
    imports: [
        VersionTestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PurchaseOrderDetailsDetailComponent,
        PurchaseOrderDetailsDialogComponent,
        PurchaseOrderDetailsDeleteDialogComponent,
        PurchaseOrderDetailsPopupComponent,
        PurchaseOrderDetailsDeletePopupComponent,
    ],
    entryComponents: [
        PurchaseOrderDetailsComponent,
        PurchaseOrderDetailsDialogComponent,
        PurchaseOrderDetailsPopupComponent,
        PurchaseOrderDetailsDeleteDialogComponent,
        PurchaseOrderDetailsDeletePopupComponent,
    ],
    providers: [
        PurchaseOrderDetailsService,
        PurchaseOrderDetailsPopupService,
        PurchaseOrderDetailsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VersionTestPurchaseOrderDetailsModule {}
