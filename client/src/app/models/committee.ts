import {CommitteeUser} from './committee-user';
import {Criteria} from './criteria';
import {Duty} from './duty';

export class Committee {
  id: string;
  introduction: string;
  name: string;
  year: string;
  members: CommitteeUser[];
  volunteers: CommitteeUser[];
  criteria: Criteria[];
  duties: Duty[];

  size(): number {
    if(this.criteria ) {
      let sizeCriteria = this.criteria.filter( crit => crit.criteria.startsWith("(size") )[0];
      let result = sizeCriteria.criteria.substring(6, sizeCriteria.criteria.length-1);
      return Number(result);
    } else {
      return 0;
    }
  }
}
