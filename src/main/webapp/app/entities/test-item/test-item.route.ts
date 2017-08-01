import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TestItemComponent } from './test-item.component';
import { TestItemDetailComponent } from './test-item-detail.component';
import { TestItemPopupComponent } from './test-item-dialog.component';
import { TestItemDeletePopupComponent } from './test-item-delete-dialog.component';

@Injectable()
export class TestItemResolvePagingParams implements Resolve<any> {

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

export const testItemRoute: Routes = [
    {
        path: 'test-item',
        component: TestItemComponent,
        resolve: {
            'pagingParams': TestItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestItems'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'test-item/:id',
        component: TestItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const testItemPopupRoute: Routes = [
    {
        path: 'test-item-new',
        component: TestItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-item/:id/edit',
        component: TestItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-item/:id/delete',
        component: TestItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
