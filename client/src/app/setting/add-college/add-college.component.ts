import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';
import {College} from '../../models/college';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-college',
  templateUrl: './add-college.component.html',
  styleUrls: ['./add-college.component.css']
})
export class AddCollegeComponent implements OnInit {
  @Output() addCollege = new EventEmitter();
  parentComponent: any;
  collegeName: string;
  constructor(private yearService: YearService, private apiService: ApiService, public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  addNewCollege() {
    const college = new College();
    college.year = this.yearService.getYearValue;
    college.name = this.collegeName;
    this.apiService.createCollege(college).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
