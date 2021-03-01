import { Component, OnInit } from '@angular/core';
import {Committee} from '../../models/committee';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-get-user-committees',
  templateUrl: './get-user-committees.component.html',
  styleUrls: ['./get-user-committees.component.css']
})
export class GetUserCommitteesComponent implements OnInit {
  constructor(
    public activeModal: NgbActiveModal,
  ) { }
  userVolunteeredCommittees: Committee[];
  userAssignedCommittees: Committee[];
  ngOnInit(): void {
  }

}
