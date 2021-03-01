import {Component, OnInit} from '@angular/core';
import {Criteria} from '../../models/criteria';
import {Duty} from '../../models/duty';
import {Committee} from '../../models/committee';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-committee',
  templateUrl: './add-committee.component.html',
  styleUrls: ['./add-committee.component.css']
})
export class AddCommitteeComponent implements OnInit {
  newIntr = '';
  Du  = '';
  newName = '';
  cri1 =  '';
  cri2 = '';
  num = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13];
  booleans = [true, false];
  modifyNewCommittee = {
    editName: false,
    editCriteria: false,
    editDuties: false,
    editIntroduction: false,
  };
  newCommittee: Committee = new Committee();
  criProperty = {
    all : ['tenured', 'admin', 'soe', 'grad-status'],
    'rank full professor': this.num,
    'rank associate professor': this.num,
    'rank assistant professor': this.num,
    soe : this.num,
    tenured: this.num,
    admin: this.num,
    size: this.num,
    chair: this.num
  };
  constructor(
    private yearService: YearService,
    private apiService: ApiService,
    public activeModal: NgbActiveModal) { }
  ngOnInit(): void {
    this.apiService.getDeptByYear(this.yearService.getYearValue).subscribe(
      depts => {
        depts.forEach(
          dept => {
            this.criProperty[`department ` + dept.name] = this.num;
          }
        );
      }
    );
    this.apiService.getCollegeByYear(this.yearService.getYearValue).subscribe(
      colleges => {
        colleges.forEach(
          college => {
            this.criProperty[`college ` + college.name] = this.num;
          }
        );
      }
    );
    this.apiService.getGendersByYear(this.yearService.getYearValue).subscribe(
      genders => {
        genders.forEach(
          gender => {
            this.criProperty[`gender ` + gender.name] = this.num;
          }
        );
      }
    );
  }
  modifyIntr() {
    this.modifyNewCommittee.editIntroduction = !this.modifyNewCommittee.editIntroduction;
  }

  modifyDu() {
    this.modifyNewCommittee.editDuties = !this.modifyNewCommittee.editDuties;
  }

  modifyCri() {
    this.modifyNewCommittee.editCriteria = !this.modifyNewCommittee.editCriteria;
  }

  modifyName() {
    this.modifyNewCommittee.editName = !this.modifyNewCommittee.editName;
  }

  saveIntr() {
    this.modifyNewCommittee.editIntroduction = !this.modifyNewCommittee.editIntroduction;
    this.newCommittee.introduction = this.newIntr;
  }

  addCri() {
    this.modifyNewCommittee.editCriteria = !this.modifyNewCommittee.editCriteria;
    const d = new Criteria();
    if (this.newCommittee.criteria === undefined) {
      this.newCommittee.criteria = [];
    }
    d.criteria = '(' + this.cri1 + ' ' + this.cri2 + ')';
    this.newCommittee.criteria.push(d);
    this.cri1 = '';
    this.cri2 = '';
  }

  addDu() {
    this.modifyNewCommittee.editDuties = !this.modifyNewCommittee.editDuties;
    const d = new Duty();
    if (this.newCommittee.duties === undefined) {
      this.newCommittee.duties = [];
    }
    d.duty = this.Du;
    this.newCommittee.duties.push(d);
    this.Du = '';
  }
  saveName() {
    this.modifyNewCommittee.editName = !this.modifyNewCommittee.editName;
    this.newCommittee.name = this.newName;
  }
  saveAll() {
    this.newCommittee.year = this.yearService.getYearValue;
    if (this.newCommittee.duties === undefined ||
      this.newCommittee.criteria === undefined ||
      this.newCommittee.introduction === undefined ||
      this.newCommittee.name === undefined) {
      alert('All properties of committee can not be null');
    } else {
      this.apiService.createCommittee(this.newCommittee).subscribe(
        value => {
          this.activeModal.close('return');
        }
      );
    }
  }
  deleteCri(index: number) {
    this.newCommittee.criteria.splice(index, 1);
  }
}
