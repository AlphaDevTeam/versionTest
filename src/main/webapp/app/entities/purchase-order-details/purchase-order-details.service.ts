import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PurchaseOrderDetails } from './purchase-order-details.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PurchaseOrderDetailsService {

    private resourceUrl = 'api/purchase-order-details';

    constructor(private http: Http) { }

    create(purchaseOrderDetails: PurchaseOrderDetails): Observable<PurchaseOrderDetails> {
        const copy = this.convert(purchaseOrderDetails);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(purchaseOrderDetails: PurchaseOrderDetails): Observable<PurchaseOrderDetails> {
        const copy = this.convert(purchaseOrderDetails);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PurchaseOrderDetails> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(purchaseOrderDetails: PurchaseOrderDetails): PurchaseOrderDetails {
        const copy: PurchaseOrderDetails = Object.assign({}, purchaseOrderDetails);
        return copy;
    }
}
