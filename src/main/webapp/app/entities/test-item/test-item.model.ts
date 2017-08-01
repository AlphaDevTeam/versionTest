import { BaseEntity } from './../../shared';

export class TestItem implements BaseEntity {
    constructor(
        public id?: number,
        public itemName?: string,
        public itemUnit?: number,
        public itemCost?: number,
        public relatedCompany?: BaseEntity,
    ) {
    }
}
