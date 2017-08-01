import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VersionTestSharedModule } from '../../shared';
import {
    TestItemService,
    TestItemPopupService,
    TestItemComponent,
    TestItemDetailComponent,
    TestItemDialogComponent,
    TestItemPopupComponent,
    TestItemDeletePopupComponent,
    TestItemDeleteDialogComponent,
    testItemRoute,
    testItemPopupRoute,
    TestItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...testItemRoute,
    ...testItemPopupRoute,
];

@NgModule({
    imports: [
        VersionTestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TestItemComponent,
        TestItemDetailComponent,
        TestItemDialogComponent,
        TestItemDeleteDialogComponent,
        TestItemPopupComponent,
        TestItemDeletePopupComponent,
    ],
    entryComponents: [
        TestItemComponent,
        TestItemDialogComponent,
        TestItemPopupComponent,
        TestItemDeleteDialogComponent,
        TestItemDeletePopupComponent,
    ],
    providers: [
        TestItemService,
        TestItemPopupService,
        TestItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VersionTestTestItemModule {}
