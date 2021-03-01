import { Component, OnInit } from '@angular/core';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-delete-dept',
  templateUrl: './delete-dept.component.html',
  styleUrls: ['./delete-dept.component.css']
})
export class DeleteDeptComponent implements OnInit {

  deptId: string;
  constructor(
    private yearService: YearService,
    private apiService: ApiService,
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit(): void {
  }

  deleteDept() {
    this.apiService.deleteDept(this.deptId).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
