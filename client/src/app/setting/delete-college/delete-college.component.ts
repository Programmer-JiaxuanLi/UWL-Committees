import { Component, OnInit } from '@angular/core';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-delete-college',
  templateUrl: './delete-college.component.html',
  styleUrls: ['./delete-college.component.css']
})
export class DeleteCollegeComponent implements OnInit {
  collegeId: string;
  constructor(
    private yearService: YearService,
    private apiService: ApiService,
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit(): void {
  }

  deleteCollege() {
    this.apiService.deleteCollege(this.collegeId).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
