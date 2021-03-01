import {Component, OnInit} from '@angular/core';
import {Papa, ParseResult} from 'ngx-papaparse';
import {FormBuilder, FormGroup} from '@angular/forms';
import {User} from '../../models/user';
import {ApiService} from '../../service/api.service';
import {Role} from '../../models/role';
import {YearService} from '../../service/year.service';
import {Gender} from '../../models/gender';
import {College} from '../../models/college';
import {Department} from '../../models/department';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-user-from-csv',
  templateUrl: './add-user-from-csv.component.html',
  styleUrls: ['./add-user-from-csv.component.css']
})
export class AddUserFromCSVComponent implements OnInit {
  selectedFile: any;
  file: any;
  fileHeaders = new Array<any>();
  propertyMapFileName = new Map<any, any>();
  fileNameMapProperty = new Map<any, any>();
  uploadedFaculties: User[];
  csvProperties = ['first', 'last', 'rank', 'college', 'tenured', 'soe', 'adminResponsibility', 'gender', 'email', 'dept'];
  relation: FormGroup;

  genderNameAndObjMap = new Map<string, Gender>();
  collegeNameAndObjMap = new Map<string, College>();
  deptNameAndObjMap = new Map<string, Department>();
  constructor(private papa: Papa,
              private formBuilder: FormBuilder,
              private apiService: ApiService,
              private yearService: YearService,
              public activeModal: NgbActiveModal) { }
  ngOnInit(): void {
    this.yearService.getValue().subscribe(
      () => {
        this.relation = this.formBuilder.group({
          first: [''],
          last: [''],
          rank: [''],
          college: [''],
          tenured: [''],
          adminResponsibility: [''],
          soe: [''],
          gender: [''],
          email: [''],
          dept: ['']
        });
        this.apiService.getGendersByYear(this.yearService.getYearValue).subscribe(
          genders => {
            genders.forEach(
              gender => {
                this.genderNameAndObjMap.set(gender.name, gender);
              }
            );
          }
        );
        this.apiService.getCollegeByYear(this.yearService.getYearValue).subscribe(
          colleges => {
            colleges.forEach(
              college => {
                this.collegeNameAndObjMap.set(college.name, college);
              }
            );
          }
        );
        this.apiService.getDeptByYear(this.yearService.getYearValue).subscribe(
          depts => {
            depts.forEach(
              dept => {
                this.deptNameAndObjMap.set(dept.name, dept);
              }
            );
          }
        );
      }
    );
  }

  fileChanged(e) {
    this.file = e.target.files[0];
    this.propertyMapFileName = new Map<any, any>();
    this.fileNameMapProperty = new Map<any, any>();
    this.fileHeaders = new Array<any>();
    this.parseCSVFile();
  }

  parseCSVFile() {
    this.papa.parse(this.file, {
      preview: 1,
      complete: result => {
        console.log(result.data[0]);
        // iterate over every column in the first row
        result.data[0].forEach(
          header => {
            this.fileHeaders.push(header);
            this.csvProperties.forEach(
              value1 => {
                if (header.toString().toLowerCase() === value1.toString().toLowerCase()) {
                  this.propertyMapFileName.set(value1, header);
                  this.fileNameMapProperty.set(header, value1);
                  this.relation.controls['' + value1].setValue(header.toString());
                }
              }
            );
          }
        );
      }
    });
  }

  cancelSaveCSV() {
    this.activeModal.dismiss();
    this.selectedFile = null;
    this.propertyMapFileName = new Map<any, any>();
    this.fileNameMapProperty = new Map<any, any>();
  }

  propertyMapping(key: string) {
    const temp = this.propertyMapFileName.get(key);
    if (this.fileNameMapProperty.get(this.relation.controls[key].value) !== undefined) {
      this.relation.controls[this.fileNameMapProperty.get(this.relation.controls[key].value)].setValue('');
    }
    this.propertyMapFileName.set(key, this.relation.controls[key].value);
    this.propertyMapFileName.delete(this.fileNameMapProperty.get(this.relation.controls[key].value));
    this.fileNameMapProperty.delete(temp);
    this.fileNameMapProperty.set(this.relation.controls[key].value, key);
  }

  uploadFaculties() {
    this.uploadedFaculties = new Array<User>();
    this.papa.parse(this.file, {
      header: true,
      complete: result => {
        if (this.mapTheProperty(result)) {
          this.apiService.uploadFacultiesFromCSV(this.uploadedFaculties).subscribe(
            () => {
              this.activeModal.close('return');
            }
          );
        }
      }
    });
  }

  mapTheProperty(result: ParseResult): boolean {
    for (const value of result.data) {
      let index = 0;
      const faculty = new User();
      const keys = Object.keys(value);
      for (const key of keys) {
        if (this.isBooleanTypeProperty(this.fileNameMapProperty.get(this.fileHeaders[index]))) {
          if (this.isBooleanType(value[key])) {
            faculty[`` + this.fileNameMapProperty.get(this.fileHeaders[index])] = Boolean(value[key]);
          } else {
            alert(this.fileHeaders[index] + ' is not mapping');
            return false;
          }
        } else if (this.isObjectProperty(this.fileNameMapProperty.get(this.fileHeaders[index]))) {
          faculty[`` + this.fileNameMapProperty.get(this.fileHeaders[index])] =
            this.getObjByClassNameAndProperty(this.fileNameMapProperty.get(this.fileHeaders[index]), value[key]);
        } else {
          faculty[`` + this.fileNameMapProperty.get(this.fileHeaders[index])] = value[key];
        }
        faculty.year = this.yearService.getYearValue;
        const role = new Role();
        role.role = 'Normal';
        const roles = [];
        roles.push(role);
        faculty.roles = roles;
        index++;
      }
      // need to check
      if (faculty.dept !== undefined) {
        faculty.college = faculty.dept.college;
      }
      this.uploadedFaculties.push(faculty);
    }
    console.log(this.uploadedFaculties);
    return true;
  }
  getObjByClassNameAndProperty(name: string, propertyValue: string) {
    if (name === 'gender') {
      return this.genderNameAndObjMap.get(propertyValue);
    } else if (name === 'college') {
      return this.collegeNameAndObjMap.get(propertyValue);
    } else if (name === 'dept') {
      return this.deptNameAndObjMap.get(propertyValue);
    }
  }

  isObjectProperty(param: any) {
    return param === 'college' || param === 'gender' || param === 'dept';
  }
  isBooleanTypeProperty(property: any): boolean {
    return property === 'tenured' || property === 'soe' || property === 'adminResponsibility';
  }
  isBooleanType(value: string): boolean {
    return value.toLowerCase() === 'true' || value.toLowerCase() === 'false';
  }
}
