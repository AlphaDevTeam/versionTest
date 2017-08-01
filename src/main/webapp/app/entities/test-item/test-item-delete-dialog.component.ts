import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TestItem } from './test-item.model';
import { TestItemPopupService } from './test-item-popup.service';
import { TestItemService } from './test-item.service';

@Component({
    selector: 'jhi-test-item-delete-dialog',
    templateUrl: './test-item-delete-dialog.component.html'
})
export class TestItemDeleteDialogComponent {

    testItem: TestItem;

    constructor(
        private testItemService: TestItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.testItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'testItemListModification',
                content: 'Deleted an testItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-test-item-delete-popup',
    template: ''
})
export class TestItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private testItemPopupService: TestItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.testItemPopupService
                .open(TestItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
