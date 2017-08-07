import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PurchaseOrderDetailsComponent } from './purchase-order-details.component';
import { PurchaseOrderDetailsDetailComponent } from './purchase-order-details-detail.component';
import { PurchaseOrderDetailsPopupComponent } from './purchase-order-details-dialog.component';
import { PurchaseOrderDetailsDeletePopupComponent } from './purchase-order-details-delete-dialog.component';

@Injectable()
export class PurchaseOrderDetailsResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const purchaseOrderDetailsRoute: Routes = [
    {
        path: 'purchase-order-details',
        component: PurchaseOrderDetailsComponent,
        resolve: {
            'pagingParams': PurchaseOrderDetailsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'purchase-order-details/:id',
        component: PurchaseOrderDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseOrderDetailsPopupRoute: Routes = [
    {
        path: 'purchase-order-details-new',
        component: PurchaseOrderDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'purchase-order-details/:id/edit',
        component: PurchaseOrderDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'purchase-order-details/:id/delete',
        component: PurchaseOrderDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
