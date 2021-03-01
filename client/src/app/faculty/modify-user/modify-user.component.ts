import {Component, OnInit} from '@angular/core';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';
import {Gender} from '../../models/gender';
import {College} from '../../models/college';
import {Department} from '../../models/department';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {User} from '../../models/user';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modify-user',
  templateUrl: './modify-user.component.html',
  styleUrls: ['./modify-user.component.css']
})
export class ModifyUserComponent implements OnInit {
  modifyUser: User;
  constructor(
    private formBuilder: FormBuilder,
    public activeModal: NgbActiveModal,
    private yearService: YearService,
    private apiService: ApiService,
  ) {}
  editUserForm: FormGroup;
  ranks = ['Assistant', 'Associate', 'Full'];
  genders: Gender[];
  colleges: College[];
  depts: Department[];
  options = {
    rank: ['', 'Associate Professor', 'Assistant Professor', 'Full Professor'],
    college: ['', 'CASH', 'CBA', 'CSH'],
    tenured: ['', 'Yes', 'No'],
    soe: ['', 'Yes', 'No'],
    admin: ['', 'Yes', 'No'],
    chair: ['', 'Yes', 'No'],
    gender: [''],
    dept: ['']
  };

  ngOnInit(): void {
    this.editUserForm = this.formBuilder.group({
      editFirst: [''],
      editLast: [''],
      editRank: [''],
      editCollege: new FormControl(null),
      editTenured: [''],
      editAdmin: [''],
      editSoe: [''],
      editGender: new FormControl(null),
      editDept: new FormControl(null),
      editChair: ['']
    });
    this.editUserForm.controls.editFirst.setValue(this.modifyUser.first);
    this.editUserForm.controls.editLast.setValue(this.modifyUser.last);
    this.editUserForm.controls.editRank.setValue(this.modifyUser.rank);
    this.editUserForm.controls.editTenured.setValue(this.modifyUser.tenured);
    this.editUserForm.controls.editSoe.setValue(this.modifyUser.soe);
    this.editUserForm.controls.editAdmin.setValue(this.modifyUser.adminResponsibility);
    this.editUserForm.controls.editChair.setValue(this.modifyUser.chair);
    this.apiService.getGendersByYear(this.yearService.getYearValue).subscribe(
      genders => {
        this.genders = genders;
        this.options.gender = [''];
        genders.forEach(
          gender => {
            this.options.gender.push(gender.name);
          }
        );
        this.editUserForm.controls.editGender.setValue(
          this.genders[this.genders.findIndex(
            formGender => formGender.id === this.modifyUser.gender.id
          )]
        );
      }
    );
    this.apiService.getCollegeByYear(this.yearService.getYearValue).subscribe(
      colleges => {
        this.colleges = colleges;
        this.options.college = [''];
        colleges.forEach(
          college => {
            this.options.college.push(college.name);
          }
        );
        this.editUserForm.controls.editCollege.setValue(
          this.colleges[ this.colleges.findIndex(
            formCollege => formCollege.id === this.modifyUser.college.id
          )]
        );
      }
    );
    this.apiService.getDeptByYear(this.yearService.getYearValue).subscribe(
      depts => {
        this.options.dept = [''];
        this.depts = depts;
        depts.forEach(
          dept => {
            this.options.dept.push(dept.name);
          }
        );
        this.editUserForm.controls.editDept.setValue(
          this.depts[this.depts.findIndex(
            formDept => formDept.id === this.modifyUser.dept.id
          )]
        );
      }
    );
  }

  save() {
      const user = this.modifyUser;
      user.first = this.editUserForm.controls.editFirst.value;
      user.last = this.editUserForm.controls.editLast.value;
      user.rank = this.editUserForm.controls.editRank.value;
      user.tenured = this.editUserForm.controls.editTenured.value;
      user.soe = this.editUserForm.controls.editSoe.value;
      user.adminResponsibility = this.editUserForm.controls.editAdmin.value;
      user.chair = this.editUserForm.controls.editChair.value;
      user.gender = this.editUserForm.controls.editGender.value;
      user.college = this.editUserForm.controls.editCollege.value;
      user.dept = this.editUserForm.controls.editDept.value;
      this.apiService.modifyUser(user).subscribe(
        () => {
        this.activeModal.close('return');
      }
    );
  }
}
