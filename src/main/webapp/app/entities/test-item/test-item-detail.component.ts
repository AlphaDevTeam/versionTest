import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { TestItem } from './test-item.model';
import { TestItemService } from './test-item.service';

@Component({
    selector: 'jhi-test-item-detail',
    templateUrl: './test-item-detail.component.html'
})
export class TestItemDetailComponent implements OnInit, OnDestroy {

    testItem: TestItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private testItemService: TestItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTestItems();
    }

    load(id) {
        this.testItemService.find(id).subscribe((testItem) => {
            this.testItem = testItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTestItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'testItemListModification',
            (response) => this.load(this.testItem.id)
        );
    }
}
