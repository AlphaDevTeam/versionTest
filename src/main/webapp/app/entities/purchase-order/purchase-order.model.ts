import { BaseEntity } from './../../shared';

export class PurchaseOrder implements BaseEntity {
    constructor(
        public id?: number,
        public poNumber?: string,
        public orderDate?: any,
        public company?: BaseEntity,
        public purchaseOrderDetails?: BaseEntity[],
    ) {
    }
}
