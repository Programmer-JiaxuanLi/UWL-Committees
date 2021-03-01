import {Component, OnInit} from '@angular/core';
import {ApiService} from '../service/api.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AuthenticationService} from '../service/authentication.service';
import {User} from '../models/user';
import {Survey} from '../models/survey';
import {YearService} from '../service/year.service';
import {SurveyResponse} from '../models/survey-response';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SelectedCommitteeComponent } from './selected-committee/selected-committee.component';
import {TopBarService} from '../service/top-bar.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  yearsForm: FormGroup;

  survey: Survey;
  user: User;

  constructor(
    public authentication: AuthenticationService,
    private apiService: ApiService,
    private formBuilder: FormBuilder,
    private yearService: YearService,
    private authenticationService: AuthenticationService,
    private topBarService: TopBarService,
    private modalService: NgbModal ) {
  }

  ngOnInit(): void {
    this.topBarService.setTopBarName('survey');
    this.apiService.getUserYears(this.authentication.currentUserValue.email).subscribe( years => {
      this.yearService.setYears( years );
    });

    this.user = this.authenticationService.currentUserValue;
    this.yearService.setValue( this.authenticationService.currentUserValue.year );
    this.yearsForm =  this.formBuilder.group({ year: [] });

    this.apiService.getSurvey( this.authenticationService.currentUserValue.id).subscribe(
      survey => {
        survey.responses.sort( (r1, r2) => {
          const c1 = r1.committee;
          const c2 = r2.committee;
          return c1.name.localeCompare( c2.name );
        });
        this.survey = survey;
      });
  }


  openCommitteeModal(response) {
    // the response.committee object has only id and name.  Get the full object prior to invoking the modal.
    this.apiService.getCommitteeById( response.committee.id ).subscribe( commitee => {
      const modalRef = this.modalService.open(SelectedCommitteeComponent, {backdropClass: 'light-blue-backdrop'});
      modalRef.componentInstance.committee = commitee;
    }
    );
  }

  createSurvey(surveyResponse: SurveyResponse) {
    const copyObj = new SurveyResponse();
    copyObj.id = surveyResponse.id;
    copyObj.selected = !surveyResponse.selected;
    copyObj.committee = surveyResponse.committee;
    surveyResponse.selected = !surveyResponse.selected;
    this.apiService.modifySurvey(this.authenticationService.currentUserValue.id, surveyResponse.id, copyObj).subscribe(
      res => {
        surveyResponse = res;
      }
    );
  }
}
