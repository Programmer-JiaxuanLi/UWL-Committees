import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../service/authentication.service';
import {ApiService} from '../service/api.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {newArray} from '@angular/compiler/src/util';
import {HashedCommittees} from '../models/hashed-committees';
import {YearService} from '../service/year.service';
import {TopBarService} from '../service/top-bar.service';
import {College} from '../models/college';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  max = {};
  committees: HashedCommittees;
  type = 'PieChart';
  RTitle = 'Rank';
  TTitle = 'Tenured';
  CTitle = 'College';
  RData = [];
  TData = [];
  CData = [];
  CNum = {};
  RNum = {
    'Full Professor': 0,
    'Associate Professor' : 0,
   'Assistant Professor': 0
  };
  TNum = {
    true: 0,
    false: 0
  };
  years: string[];
  yearForm: FormGroup;


  constructor(
    private yearService: YearService,
    public authentication: AuthenticationService,
    private apiservice: ApiService,
    private topBarService: TopBarService,
    private formBuilder: FormBuilder) {
    this.yearService.setYears([]);
  }
  ngOnInit(): void {
    this.apiservice.getYears().subscribe( years => {
      this.yearService.setYears( years );
    });
    this.topBarService.setTopBarName('Analysis');
    this.yearForm = this.formBuilder.group({
      startYear: [''],
      endYear: ['']
    });
  }

  get f() { return this.yearForm.controls; }
  search() {
    this.apiservice.getHashedCommitteesByYears(this.f.startYear.value, this.f.endYear.value).subscribe(
      value => {
        this.committees = value;
        this.committeeMemberMaxLength(value);
        this.initPiecgart(value);
        this.years =  newArray( this.f.endYear.value - this.f.startYear.value + 1);
        for (let i = 0; i < this.f.endYear.value - this.f.startYear.value + 1; i++) {
          // tslint:disable-next-line:radix
          this.years[i] = ( parseInt(this.f.startYear.value) + i).toString();
        }
      }
    );
  }

  private initPiecgart(committees: HashedCommittees) {
    this.apiservice.getCollegeByYears(this.f.startYear.value, this.f.endYear.value ).subscribe(
      colleges => {
        const collegeSet = new Set();
        colleges.forEach(
          college => {
            if (!collegeSet.has(college.name)) {
              this.CNum[college.name] = 0;
              collegeSet.add(college.name);
            }
          }
        );
        // tslint:disable-next-line:forin
        for (const property in committees) {
          // tslint:disable-next-line:prefer-for-of
          for (let j = 0; j < committees[property].length; j++) {
            Object.keys(this.RNum).forEach(
              rankName => {
                this.RNum[rankName] += committees[property][j].members.filter(
                  item => item.rank === rankName).length;
              }
            );
            Object.keys(this.TNum).forEach(
              tenuredName => {
                this.TNum[tenuredName] += committees[property][j].members.filter(
                  item => '' + item.tenured === tenuredName).length;
              }
            );
            Object.keys(this.CNum).forEach(
              collegeName => {
                this.CNum[collegeName] += committees[property][j].members.filter(
                  item => item.college.name === collegeName).length;
              }
            );
          }
        }
        // tslint:disable-next-line:forin
        for (const property in committees) {
          // tslint:disable-next-line:prefer-for-of
          for (let j = 0; j < committees[property].length; j++) {
            Object.keys(this.CNum).forEach(
              collegeName => {
                this.CNum[collegeName] += committees[property][j].members.filter(
                  item => item.college.name === collegeName).length;
              }
            );
          }
        }
        Object.keys(this.CNum).forEach(
          key => {
            const arr = [];
            arr.push(key);
            arr.push(this.CNum[key]);
            this.CData.push(arr);
          }
        );
        Object.keys(this.TNum).forEach(
          key => {
            const arr = [];
            arr.push(key);
            arr.push(this.TNum[key]);
            this.TData.push(arr);
          }
        );
        Object.keys(this.RNum).forEach(
          key => {
            const arr = [];
            arr.push(key);
            arr.push(this.RNum[key]);
            this.RData.push(arr);
          }
        );
      }
    );
  }

  committeeMemberMaxLength(committees) {
    for (const property of committees) {
      let maxNum = 0;
      committees[property].forEach(
        value => {
          if  (value.members.length > maxNum) {
            maxNum = value.members.length;
          }
        }
      );
      this.max[property] = maxNum;
    }
  }
}
