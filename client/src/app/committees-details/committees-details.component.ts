import {Component, OnInit} from '@angular/core';
import {YearService} from '../service/year.service';
import {ApiService} from '../service/api.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Committee} from '../models/committee';
import {forkJoin} from 'rxjs';
import {AuthenticationService} from '../service/authentication.service';
import { faCircle, faCheckCircle, faAngleDown, faAngleUp } from '@fortawesome/free-solid-svg-icons';
import {TopBarService} from '../service/top-bar.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AssignMemberComponent} from './assign-member/assign-member.component';
import {RemoveMemberComponent} from './remove-member/remove-member.component';

@Component({
  selector: 'app-committees-details',
  templateUrl: './committees-details.component.html',
  styleUrls: ['./committees-details.component.css']
})
export class CommitteesDetailsComponent implements OnInit {
  committee: Committee;
  usersCommittees: Committee[][];
  volunteersCommittees: Committee[][];

  views = {
    introduction: false,
    duties : false,
    criteria : false,
    members: true,
    volunteers: true
  };

  icons = {
    faCircle,
    faCheckCircle,
    faAngleDown,
    faAngleUp
  };

  constructor(
    private modalService: NgbModal,
    public authentication: AuthenticationService,
    private topBarService: TopBarService,
    public yearService: YearService,
    private apiService: ApiService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.topBarService.setTopBarName('Committee Details');
    this.route.params.subscribe(
      param => {
        this.initCommitteeById( param.id );
      },
      (err) => console.error(err),
    );
  }
  toggleView( view: string ): void {
    this.views[view] = !this.views[view];
  }
  initCommitteeById(committeeId: string) {
     this.apiService.getCommitteeById(committeeId).subscribe (
      committee => {
        this.apiService.getCommitteeYears(committeeId).subscribe(
          years => {
            this.yearService.setYears(years);
          }
        );
        if ( committee.name === this.yearService.getCommitteeNameValue()) {
          this.yearService.committeeGetValue().subscribe(
            year => {
              if (year !== undefined && year !== '') {
                this.apiService.getCommitteeByYearAndName(year, this.yearService.getCommitteeNameValue()).subscribe(
                  newCommittee => {
                    console.log( this.yearService.getCommitteeNameValue());
                    if ( newCommittee.id !== this.committee.id && this.committee.name === this.yearService.getCommitteeNameValue()) {
                      this.yearService.setCommitteeName(newCommittee.name);
                      this.router.navigate(['/uwl/committees' , newCommittee.id] , { fragment: year});
                    }
                  }
                );
              }
            }
          );
        }
        this.uploadVolunteerAndUserCommittees(committee);
      }
    );
  }
  uploadCommitteeById(committeeId: string) {
    this.apiService.getCommitteeById(committeeId).subscribe (
      committee => {
        this.uploadVolunteerAndUserCommittees(committee);
      }
    );
  }
  uploadVolunteerAndUserCommittees(committee: Committee) {
    this.committee = committee;
    this.committee.members.forEach( m => m.rank = m.rank.split(' ')[0] );
    this.committee.volunteers.forEach( m => m.rank = m.rank.split(' ')[0] );

    const assignedUsersReqs = committee.members.map( m => this.apiService.getUserAssignedCommittees(m.id) );
    forkJoin(assignedUsersReqs).subscribe(
      results => {
        this.usersCommittees = results as Committee[][];
      }
    );

    const unassignedUserReqs = this.committee.volunteers.map( m => this.apiService.getUserAssignedCommittees(m.id) );
    forkJoin(unassignedUserReqs).subscribe(
      results => {
        this.volunteersCommittees = results as Committee[][];
      }
    );
  }
  deleteUser(id): void {
    const modalRef = this.modalService.open(RemoveMemberComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.componentInstance.committeeId = this.committee.id;
    modalRef.componentInstance.userId = id;
    modalRef.result.then(() => {
      this.uploadCommitteeById(this.committee.id);
    });
  }
  assignUser(id): void {
    const modalRef = this.modalService.open(AssignMemberComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.componentInstance.committeeId = this.committee.id;
    modalRef.componentInstance.userId = id;
    modalRef.result.then(() => {
      this.uploadCommitteeById(this.committee.id);
    });
  }
}
