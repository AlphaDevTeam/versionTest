import { BaseEntity, User } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public companyName?: string,
        public relatedUser?: User,
    ) {
    }
}
