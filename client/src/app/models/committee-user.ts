import {Gender} from './gender';
import {College} from './college';

export class CommitteeUser {
  id: string;
  first: string;
  last: string;
  rank: string;
  college: College;
  tenured: boolean;
  soe: boolean;
  adminResponsibility: boolean;
  gender: Gender;
  year: string;
}
