import {UserCommittees} from './user-committees';
import {Role} from './role';
import {Gender} from './gender';
import {College} from './college';
import {Department} from './department';

export class User {
  id: string;
  email: string;
  first: string;
  last: string;
  rank: string;
  college: College;
  tenured: boolean;
  soe: boolean;
  adminResponsibility: boolean;
  gender: Gender;
  year: string;
  committees: UserCommittees[];
  roles: Role[];
  dept: Department;
  chair: boolean;
  years: string[];
}
