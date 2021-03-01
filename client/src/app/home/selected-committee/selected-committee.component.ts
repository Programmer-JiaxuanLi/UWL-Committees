import {Component, OnInit} from '@angular/core';
import {Committee} from '../../models/committee';
import { faCircle, faCheckCircle, faAngleDown, faAngleUp } from '@fortawesome/free-solid-svg-icons';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-selected-committee',
  templateUrl: './selected-committee.component.html',
  styleUrls: ['./selected-committee.component.css']
})
export class SelectedCommitteeComponent implements OnInit {
  committee: Committee;
  views = {
    introduction: false,
    duties : false,
    criteria : false
  };

  icons = {
    faCircle,
    faCheckCircle,
    faAngleDown,
    faAngleUp
  };

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  toggleView( view: string ): void {
    this.views[view] = !this.views[view];
  }
}
