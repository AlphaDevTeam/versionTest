import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VersionTestExtraUserModule } from './extra-user/extra-user.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        VersionTestExtraUserModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VersionTestEntityModule {}
