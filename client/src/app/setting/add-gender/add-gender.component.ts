import {Component, OnInit} from '@angular/core';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';
import {Gender} from '../../models/gender';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-gender',
  templateUrl: './add-gender.component.html',
  styleUrls: ['./add-gender.component.css']
})
export class AddGenderComponent implements OnInit {
  genName: string;
  constructor(private yearService: YearService, private apiService: ApiService, public activeModal: NgbActiveModal) { }
  ngOnInit(): void {
  }

  addNewGen() {
    const gen = new Gender();
    gen.name = this.genName;
    gen.year = this.yearService.getYearValue;
    this.apiService.createGender(gen).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
