import { Component, OnInit } from '@angular/core';
import {ApiService} from '../../service/api.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-create-year',
  templateUrl: './create-year.component.html',
  styleUrls: ['./create-year.component.css']
})
export class CreateYearComponent implements OnInit {
  newYear: number;
  constructor(
    public activeModal: NgbActiveModal,
    private apiService: ApiService
  ) { }

  ngOnInit(): void {
  }
  createYear() {
    this.activeModal.close('return');
    this.apiService.createYear(this.newYear.toString()).subscribe(
      value => {
      }
    );
  }
}
