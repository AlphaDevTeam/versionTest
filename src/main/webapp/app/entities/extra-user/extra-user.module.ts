import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VersionTestSharedModule } from '../../shared';
import { VersionTestAdminModule } from '../../admin/admin.module';
import {
    ExtraUserService,
    ExtraUserPopupService,
    ExtraUserComponent,
    ExtraUserDetailComponent,
    ExtraUserDialogComponent,
    ExtraUserPopupComponent,
    ExtraUserDeletePopupComponent,
    ExtraUserDeleteDialogComponent,
    extraUserRoute,
    extraUserPopupRoute,
    ExtraUserResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...extraUserRoute,
    ...extraUserPopupRoute,
];

@NgModule({
    imports: [
        VersionTestSharedModule,
        VersionTestAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExtraUserComponent,
        ExtraUserDetailComponent,
        ExtraUserDialogComponent,
        ExtraUserDeleteDialogComponent,
        ExtraUserPopupComponent,
        ExtraUserDeletePopupComponent,
    ],
    entryComponents: [
        ExtraUserComponent,
        ExtraUserDialogComponent,
        ExtraUserPopupComponent,
        ExtraUserDeleteDialogComponent,
        ExtraUserDeletePopupComponent,
    ],
    providers: [
        ExtraUserService,
        ExtraUserPopupService,
        ExtraUserResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VersionTestExtraUserModule {}
