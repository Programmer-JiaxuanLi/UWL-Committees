import { Component, OnInit } from '@angular/core';
import { Router} from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AuthenticationService } from '../service/authentication.service';
import { YearService } from '../service/year.service';
import { ApiService } from '../service/api.service';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private yearService: YearService,
    private apiService: ApiService
  ) {
    // redirect to home if already logged in
    this.authenticationService.getUser().subscribe( user => {
      if (user !== null) {
        this.navigateToDefaultPage();
      }
    });
  }

  navigateToDefaultPage( ) {
    if (this.authenticationService.hasRole( 'Admin' ) ) {
      this.apiService.getYears().subscribe(
        years => {
          this.yearService.setYears(years);
          this.router.navigate(['/uwl/faculty'], {fragment: years[0]});
        }
      );
    } else if (this.authenticationService.hasRole('Nominate') ) {
      this.apiService.getYears().subscribe(
        years => {
          this.router.navigate(['/uwl/committees'], {fragment: years[0]});
        }
      );
    } else {
      this.router.navigate(['/uwl/survey'],  {fragment: this.authenticationService.currentUserValue.years[0]});
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: [''],
      password: ['']
    });
  }
  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;
    this.authenticationService.login(this.f.email.value)
      .pipe(first())
      .subscribe(
        user => {
          console.log( 'LOGIN RETURNED: ' + user);
          this.navigateToDefaultPage();
        },
        error => {
          this.loading = false;
        });
  }
}
