import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../service/authentication.service';
import {YearService} from '../service/year.service';
import {ApiService} from '../service/api.service';
import {Gender} from '../models/gender';
import {College} from '../models/college';
import {Department} from '../models/department';

import { faTrash, faEdit, faPlusSquare } from '@fortawesome/free-solid-svg-icons';
import {TopBarService} from '../service/top-bar.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AddDeptComponent} from './add-dept/add-dept.component';
import {AddGenderComponent} from './add-gender/add-gender.component';
import {AddCollegeComponent} from './add-college/add-college.component';
import {EditDeptComponent} from './edit-dept/edit-dept.component';
import {EditGenderComponent} from './edit-gender/edit-gender.component';
import {EditCollegeComponent} from './edit-college/edit-college.component';
import {DeleteCollegeComponent} from './delete-college/delete-college.component';
import {DeleteGenComponent} from './delete-gen/delete-gen.component';
import {DeleteDeptComponent} from './delete-dept/delete-dept.component';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  add = {
    addCollege: false,
    addGender: false,
    addDept: false,
  };


  genders: Gender[] = [];
  colleges: College[] = [];
  depts: Department[] = [];

  icons = {
    faTrash,
    faEdit,
    faPlusSquare
  };

  constructor(
    public authentication: AuthenticationService,
    private yearService: YearService,
    private apiService: ApiService,
    private topBarService: TopBarService,
    private modalService: NgbModal) {
    this.apiService.getYears().subscribe( years => {
      this.yearService.setYears( years );
    });
  }

  ngOnInit(): void {
    this.topBarService.setTopBarName('Settings');
    this.yearService.getValue().subscribe(
      () => {
        this.getCollege();
        this.getGen();
        this.getDept();
      }
    );
  }

  deleteDept(deptId: string) {
    const modalRef = this.modalService.open(DeleteDeptComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.componentInstance.deptId = deptId;
    modalRef.result.then(
      () => {
        this.getGen();
        this.getDept();
        this.getCollege();
      }
    );
  }
  deleteGender(genderId: string) {
    const modalRef = this.modalService.open(DeleteGenComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.componentInstance.genId = genderId;
    modalRef.result.then(
      () => {
        this.getGen();
        this.getDept();
        this.getCollege();
      }
    );
  }
  deleteCollege(collegeId: string) {
    const modalRef = this.modalService.open(DeleteCollegeComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.componentInstance.collegeId = collegeId;
    modalRef.result.then(
      () => {
        this.getGen();
        this.getDept();
        this.getCollege();
      }
    );
  }
  editDept(dept: Department) {
    const modalRef = this.modalService.open(EditDeptComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.componentInstance.dept = dept;
    modalRef.result.then(
      () => {
        this.getDept();
      }
    );
  }
  editGender(gen: Gender) {
    const modalRef = this.modalService.open(EditGenderComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.componentInstance.gender = gen;
    modalRef.result.then(
      () => {
        this.getGen();
      }
    );
  }
  editCollege(college: College) {
    const modalRef = this.modalService.open(EditCollegeComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.componentInstance.college = college;
    modalRef.result.then(
      () => {
        this.getCollege();
      }
    );
  }

  getCollege() {
    this.apiService.getCollegeByYear(this.yearService.getYearValue).subscribe(
      colleges => {
        console.log(colleges);
        this.colleges = colleges;
      }
    );
  }

  getGen() {
    this.apiService.getGendersByYear(this.yearService.getYearValue).subscribe(
      genders => {
        console.log(genders);
        this.genders = genders;
      }
    );
  }

  getDept() {
    this.apiService.getDeptByYear(this.yearService.getYearValue).subscribe(
      depts => {
        console.log(depts);
        this.depts = depts;
      }
    );
  }

  addDept() {
    const modalRef = this.modalService.open(AddDeptComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.componentInstance.parentComponent = this;
    modalRef.result.then(
      () => {
        this.getDept();
      }
    );
  }

  addGen() {
    const modalRef = this.modalService.open(AddGenderComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.result.then(
      () => {
        this.getGen();
      }
    );
  }

  addCollege() {
    const modalRef = this.modalService.open(AddCollegeComponent, {backdropClass: 'light-blue-backdrop'});
    modalRef.result.then(
      () => {
        this.getCollege();
      }
    );
  }
}
