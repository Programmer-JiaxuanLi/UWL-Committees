import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {ApiService} from '../../service/api.service';
import {YearService} from '../../service/year.service';
import {Gender} from '../../models/gender';
import {College} from '../../models/college';
import {Department} from '../../models/department';
import {User} from '../../models/user';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Role} from "../../models/role";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  genders: Gender[];
  colleges: College[];
  depts: Department[];
  addUserForm: FormGroup;
  constructor(private formBuilder: FormBuilder,
              private yearService: YearService,
              private apiService: ApiService,
              public activeModal: NgbActiveModal) {
    this.addUserForm = this.formBuilder.group({
      email: [''],
      first: [''],
      last: [''],
      rank: ['Full Professor'],
      college: new FormControl(null),
      tenured: [false],
      admin: [false],
      soe: [false],
      gender: new FormControl(null),
      chair: [false],
      dept: new FormControl(null),
    });
  }
  ngOnInit(): void {
    this.apiService.getGendersByYear(this.yearService.getYearValue).subscribe(
      genders => {
        this.genders = genders;
        this.addUserForm.controls.gender.setValue(this.genders[0]);
      }
    );
    this.apiService.getCollegeByYear(this.yearService.getYearValue).subscribe(
      colleges => {
        this.colleges = colleges;
        this.addUserForm.controls.college.setValue(this.colleges[0]);
      }
    );
    this.apiService.getDeptByYear(this.yearService.getYearValue).subscribe(
      depts => {
        this.depts = depts;
        this.addUserForm.controls.dept.setValue(this.depts[0]);
      }
    );
  }
  addFaculty(): void {
    const user = new User();
    Object.keys(this.addUserForm.controls).forEach(
      key => {
        user[key] = this.addUserForm.controls[key].value;
      }
    );
    user.year =  this.yearService.getYearValue;
    const userRole = new Role();
    userRole.role = 'Normal'
    user.roles = [];
    user.roles.push(userRole);
    this.apiService.createUser(user).
    subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
