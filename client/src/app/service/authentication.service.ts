import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppConstants } from '../constants/app-constants';
import { User } from '../models/user';
import {Router} from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private static USER_STORE = 'coc-user';
  private currentUserSubject: BehaviorSubject<User>;
  private currentUser: Observable<User>;
  private roles: string[];

  constructor(private http: HttpClient, private router: Router) {
    this.loadUser();
  }

  public getUser(): Observable<User> {
    return this.currentUser;
  }
  private loadUser() {
    let user = JSON.parse( localStorage.getItem( AuthenticationService.USER_STORE ) );
    this.currentUserSubject = new BehaviorSubject<User>( user );
    this.currentUser = this.currentUserSubject.asObservable();
    this.extractRoles( user );
  }

  private storeUser( user ): User {
    localStorage.setItem( AuthenticationService.USER_STORE, JSON.stringify(user));
    this.currentUserSubject.next( user );
    return user;
  }

  private deleteUser() {
    localStorage.removeItem( AuthenticationService.USER_STORE );
    this.currentUserSubject.next( null );
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }
  public hasRole( role ) {
    return this.roles.indexOf(role) >= 0;
  }
  private extractRoles( user ) {
    this.roles = user && (user.roles.map( role => role.role ) );
  }
  login( email ) {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    const data = {  "email" : email};
    return this.http.post<User>(`${AppConstants.LOGIN_URL}`, data, config)
      .pipe(map(user => {
        this.http.get<string[]>(`${AppConstants.API_URL}/users/email/${email}/years`, {} ).subscribe(
          value => {
            user.years = value;
            console.log(user);
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            this.extractRoles(user);
            return this.storeUser( user );
          }
        );
      }));
  }
  logout() {
    this.deleteUser();
    this.router.navigate(['/']);
  }
}
