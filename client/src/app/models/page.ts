import {User} from './user';

export class Page {
  content: User[];
  totalPages: number;
  number: number;
  first : boolean;
  last : boolean;
}
