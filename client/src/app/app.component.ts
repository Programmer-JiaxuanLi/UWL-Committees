import {Component, OnInit} from '@angular/core';
import {ApiService} from './service/api.service';
import {FormBuilder} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from './service/authentication.service';
import {User} from './models/user';
import { AfterViewInit } from '@angular/core';
import * as Feather from 'feather-icons';
import {YearService} from './service/year.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {
  title = 'committees';
  user: User;
  constructor(
    private yearService: YearService,
    private apiService: ApiService,
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService) {
  }

  ngAfterViewInit() {
    Feather.replace();
  }
  ngOnInit(): void {
    this.authenticationService.getUser().subscribe(
      value => {
        this.user = value;
      }
    );
  }
}
