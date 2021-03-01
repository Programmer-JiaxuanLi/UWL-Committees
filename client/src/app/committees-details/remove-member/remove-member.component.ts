import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {ApiService} from '../../service/api.service';

@Component({
  selector: 'app-remove-member',
  templateUrl: './remove-member.component.html',
  styleUrls: ['./remove-member.component.css']
})
export class RemoveMemberComponent implements OnInit {
  public userId: string;
  public committeeId: string;
  constructor(
    public activeModal: NgbActiveModal,
    public apiService: ApiService) { }
  ngOnInit(): void {
  }
  removeUser() {
    this.activeModal.dismiss();
    this.apiService.removeUserFromCommittee(this.committeeId, this.userId).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
