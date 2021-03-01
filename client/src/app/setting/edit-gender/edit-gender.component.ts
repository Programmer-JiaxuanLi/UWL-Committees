import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Gender} from '../../models/gender';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';

@Component({
  selector: 'app-edit-gender',
  templateUrl: './edit-gender.component.html',
  styleUrls: ['./edit-gender.component.css']
})
export class EditGenderComponent implements OnInit {
  editGenderText: string;
  gender: Gender;
  constructor(
    private yearService: YearService,
    private apiService: ApiService,
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit(): void {
  }
  saveGender() {
    this.gender.name = this.editGenderText;
    this.apiService.modifyGender(this.gender).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
