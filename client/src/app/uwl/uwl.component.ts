import { Component, OnInit } from '@angular/core';
import {User} from '../models/user';
import {YearService} from '../service/year.service';
import {ApiService} from '../service/api.service';
import {FormBuilder} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../service/authentication.service';

@Component({
  selector: 'app-uwl',
  templateUrl: './uwl.component.html',
  styleUrls: ['./uwl.component.css']
})
export class UWLComponent implements OnInit {
  title = 'committees';
  user: User;
  constructor(private yearService: YearService, private apiService: ApiService, private formBuilder: FormBuilder, private router: Router,
              private authenticationService: AuthenticationService) {}
  ngOnInit(): void {
    this.authenticationService.getUser().subscribe(
      value => {
        this.user = value;
      }
    );
  }

}
