import { Component, OnInit } from '@angular/core';
import {YearService} from '../service/year.service';
import {ApiService} from '../service/api.service';
import {AuthenticationService} from '../service/authentication.service';
import {CommitteeSummary} from '../models/committee-summary';
import { faTimesCircle, faCheckCircle, faTrash} from '@fortawesome/free-solid-svg-icons';
import {TopBarService} from '../service/top-bar.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AddCommitteeComponent} from './add-committee/add-committee.component';

@Component({
  selector: 'app-committees-list',
  templateUrl: './committees-list.component.html',
  styleUrls: ['./committees-list.component.css']
})
export class CommitteesListComponent implements OnInit {
  committees: CommitteeSummary[] = [];
  committeesCriteriaStatus: any;

  icons = {
    faTimesCircle,
    faCheckCircle,
    faTrash
  };

  constructor(
    private modalService: NgbModal,
    public authentication: AuthenticationService,
    private yearService: YearService,
    private apiService: ApiService,
    private topBarService: TopBarService) {}

  ngOnInit(): void {
    this.topBarService.setTopBarName('Committees');
    this.getCommitteeList();
  }

  getCommitteeList() {
    this.apiService.getYears().subscribe( years => {
      this.yearService.setYears( years );
    });

    this.yearService.getValue().subscribe(
      year => {
        if (year !== undefined && year !== '' && year !== null) {
          this.apiService.getCommitteesByYear(year).subscribe(
            committees => {
              this.committees = committees;
              this.apiService.getCommitteeCriteriaStatus(committees).subscribe(
                status => {
                  this.committeesCriteriaStatus = status as number[];
                }
              );
            }
          );
        }
      }
    );
  }
  delete(committee: CommitteeSummary, i: number) {
    this.apiService.deleteCommittee(committee.id).subscribe();
    this.committees.splice(i, 1);
  }

  addCommittee() {
    const modalRef = this.modalService.open(AddCommitteeComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.result.then(
      () => {
        this.getCommitteeList();
      }
    );
  }

  setCommitteeName(name: string) {
    this.yearService.setCommitteeName(name);
  }
}
