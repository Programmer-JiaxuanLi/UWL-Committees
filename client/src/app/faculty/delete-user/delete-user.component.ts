import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.css']
})
export class DeleteUserComponent implements OnInit {
  constructor(
    public activeModal: NgbActiveModal,
  ) {}
  ngOnInit(): void {
  }
  deleteUser() {
    this.activeModal.close('return');
  }
}
