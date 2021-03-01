import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Department} from '../../models/department';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';

@Component({
  selector: 'app-edit-dept',
  templateUrl: './edit-dept.component.html',
  styleUrls: ['./edit-dept.component.css']
})
export class EditDeptComponent implements OnInit {

  editDeptText: string;
  dept: Department;
  constructor(
    private yearService: YearService,
    private apiService: ApiService,
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit(): void {
  }
  saveDept() {
    this.dept.name = this.editDeptText;
    this.apiService.modifyDept(this.dept).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }

}
