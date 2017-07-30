import { platformBrowser } from '@angular/platform-browser';
import { ProdConfig } from './blocks/config/prod.config';
import { VersionTestAppModuleNgFactory } from '../../../../build/aot/src/main/webapp/app/app.module.ngfactory';

ProdConfig();

platformBrowser().bootstrapModuleFactory(VersionTestAppModuleNgFactory)
.then((success) => console.log(`Application started`))
.catch((err) => console.error(err));
