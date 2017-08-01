import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ExtraUser } from './extra-user.model';
import { ExtraUserService } from './extra-user.service';

@Component({
    selector: 'jhi-extra-user-detail',
    templateUrl: './extra-user-detail.component.html'
})
export class ExtraUserDetailComponent implements OnInit, OnDestroy {

    extraUser: ExtraUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private extraUserService: ExtraUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExtraUsers();
    }

    load(id) {
        this.extraUserService.find(id).subscribe((extraUser) => {
            this.extraUser = extraUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExtraUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'extraUserListModification',
            (response) => this.load(this.extraUser.id)
        );
    }
}
