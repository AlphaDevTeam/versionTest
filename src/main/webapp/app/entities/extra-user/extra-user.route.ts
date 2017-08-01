import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExtraUserComponent } from './extra-user.component';
import { ExtraUserDetailComponent } from './extra-user-detail.component';
import { ExtraUserPopupComponent } from './extra-user-dialog.component';
import { ExtraUserDeletePopupComponent } from './extra-user-delete-dialog.component';

@Injectable()
export class ExtraUserResolvePagingParams implements Resolve<any> {

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

export const extraUserRoute: Routes = [
    {
        path: 'extra-user',
        component: ExtraUserComponent,
        resolve: {
            'pagingParams': ExtraUserResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExtraUsers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'extra-user/:id',
        component: ExtraUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExtraUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const extraUserPopupRoute: Routes = [
    {
        path: 'extra-user-new',
        component: ExtraUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExtraUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'extra-user/:id/edit',
        component: ExtraUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExtraUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'extra-user/:id/delete',
        component: ExtraUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExtraUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
