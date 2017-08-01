/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VersionTestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ExtraUserDetailComponent } from '../../../../../../main/webapp/app/entities/extra-user/extra-user-detail.component';
import { ExtraUserService } from '../../../../../../main/webapp/app/entities/extra-user/extra-user.service';
import { ExtraUser } from '../../../../../../main/webapp/app/entities/extra-user/extra-user.model';

describe('Component Tests', () => {

    describe('ExtraUser Management Detail Component', () => {
        let comp: ExtraUserDetailComponent;
        let fixture: ComponentFixture<ExtraUserDetailComponent>;
        let service: ExtraUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VersionTestTestModule],
                declarations: [ExtraUserDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ExtraUserService,
                    JhiEventManager
                ]
            }).overrideTemplate(ExtraUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExtraUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExtraUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ExtraUser(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.extraUser).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
