import { BaseEntity } from './../../shared';

export class PurchaseOrderDetails implements BaseEntity {
    constructor(
        public id?: number,
        public qty?: number,
        public ref1?: string,
        public company?: BaseEntity,
        public purchaseOrder?: BaseEntity,
        public testItem?: BaseEntity,
    ) {
    }
}
