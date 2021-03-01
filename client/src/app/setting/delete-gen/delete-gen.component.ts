import { Component, OnInit } from '@angular/core';
import {YearService} from '../../service/year.service';
import {ApiService} from '../../service/api.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-delete-gen',
  templateUrl: './delete-gen.component.html',
  styleUrls: ['./delete-gen.component.css']
})
export class DeleteGenComponent implements OnInit {

  genId: string;
  constructor(
    private yearService: YearService,
    private apiService: ApiService,
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit(): void {
  }

  deleteGen() {
    this.apiService.deleteGender(this.genId).subscribe(
      () => {
        this.activeModal.close('return');
      }
    );
  }
}
