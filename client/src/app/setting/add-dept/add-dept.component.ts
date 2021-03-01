import {Component, OnInit} from '@angular/core';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';
import {Department} from '../../models/department';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-dept',
  templateUrl: './add-dept.component.html',
  styleUrls: ['./add-dept.component.css']
})
export class AddDeptComponent implements OnInit {
  deptName: string;
  constructor(private yearService: YearService, private apiService: ApiService, public activeModal: NgbActiveModal) { }
  ngOnInit(): void {
  }

  addNewDept() {
    const dept = new Department();
    dept.name = this.deptName;
    dept.year = this.yearService.getYearValue;
    this.apiService.createDept(dept).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
