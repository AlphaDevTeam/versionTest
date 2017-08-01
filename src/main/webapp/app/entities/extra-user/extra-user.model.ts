import { BaseEntity, User } from './../../shared';

export class ExtraUser implements BaseEntity {
    constructor(
        public id?: number,
        public extraInfo?: string,
        public user?: User,
    ) {
    }
}
