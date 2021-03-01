import {Component, OnInit} from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {ApiService} from '../../service/api.service';

@Component({
  selector: 'app-assign-member',
  templateUrl: './assign-member.component.html',
  styleUrls: ['./assign-member.component.css']
})
export class AssignMemberComponent implements OnInit {
  public userId: string;
  public committeeId: string;
  constructor(
    public activeModal: NgbActiveModal,
    public apiService: ApiService) { }

  ngOnInit(): void {
  }

  assignMember() {
    this.apiService.assignUserToOneCommittee(this.committeeId, this.userId).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
