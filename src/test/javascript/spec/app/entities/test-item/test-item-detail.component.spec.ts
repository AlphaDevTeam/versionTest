/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VersionTestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TestItemDetailComponent } from '../../../../../../main/webapp/app/entities/test-item/test-item-detail.component';
import { TestItemService } from '../../../../../../main/webapp/app/entities/test-item/test-item.service';
import { TestItem } from '../../../../../../main/webapp/app/entities/test-item/test-item.model';

describe('Component Tests', () => {

    describe('TestItem Management Detail Component', () => {
        let comp: TestItemDetailComponent;
        let fixture: ComponentFixture<TestItemDetailComponent>;
        let service: TestItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VersionTestTestModule],
                declarations: [TestItemDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TestItemService,
                    JhiEventManager
                ]
            }).overrideTemplate(TestItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TestItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TestItem(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.testItem).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
