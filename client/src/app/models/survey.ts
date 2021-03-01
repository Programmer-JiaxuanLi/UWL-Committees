import {SurveyResponse} from './survey-response';

export class Survey {
  id: number;
  userId: number;
  year: string;
  comment: string;
  responses: SurveyResponse[];
}
