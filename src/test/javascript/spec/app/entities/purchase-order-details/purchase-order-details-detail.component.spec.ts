/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VersionTestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PurchaseOrderDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/purchase-order-details/purchase-order-details-detail.component';
import { PurchaseOrderDetailsService } from '../../../../../../main/webapp/app/entities/purchase-order-details/purchase-order-details.service';
import { PurchaseOrderDetails } from '../../../../../../main/webapp/app/entities/purchase-order-details/purchase-order-details.model';

describe('Component Tests', () => {

    describe('PurchaseOrderDetails Management Detail Component', () => {
        let comp: PurchaseOrderDetailsDetailComponent;
        let fixture: ComponentFixture<PurchaseOrderDetailsDetailComponent>;
        let service: PurchaseOrderDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VersionTestTestModule],
                declarations: [PurchaseOrderDetailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PurchaseOrderDetailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(PurchaseOrderDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PurchaseOrderDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseOrderDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PurchaseOrderDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.purchaseOrderDetails).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
