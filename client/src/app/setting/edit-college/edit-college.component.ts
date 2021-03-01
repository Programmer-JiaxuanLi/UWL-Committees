import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {College} from '../../models/college';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';

@Component({
  selector: 'app-edit-college',
  templateUrl: './edit-college.component.html',
  styleUrls: ['./edit-college.component.css']
})
export class EditCollegeComponent implements OnInit {
  editCollegeText: string;
  college: College;
  constructor(
    private yearService: YearService,
    private apiService: ApiService,
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit(): void {
  }
  saveCollege() {
    this.college.name = this.editCollegeText;
    this.apiService.modifyCollege(this.college).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
