import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {ActivatedRoute, Router, ActivationEnd} from '@angular/router';
import { map, filter } from 'rxjs/operators';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class YearService {
  private committeeName: BehaviorSubject<string> = new BehaviorSubject<string>('');
  private currentYear: BehaviorSubject<string> = new BehaviorSubject<string>('');
  private yearForCommitteePage: BehaviorSubject<string> = new BehaviorSubject<string>('');
  private years: BehaviorSubject<string[]> = new BehaviorSubject<string[]>([]);
  private path: string;
  private committeeId: string;

  constructor(public authentication: AuthenticationService, private router: Router,
              private route: ActivatedRoute) {
    this.route.fragment.subscribe((fragment: string) => {
      if (/^[1-9]\d{3}$/.test(fragment)) {
        this.currentYear.next(fragment);
      }
    });

    this.router.events
      .pipe( filter(e => (e instanceof ActivationEnd) ),
             map(e => e instanceof ActivationEnd ? e.snapshot : {} )
      ).subscribe( state => {
      // @ts-ignore
        if (state.url.length >= 2) {
          // @ts-ignore
          this.path = state.url[0].path;
          // @ts-ignore
          this.committeeId = state.url[1].path;
          // @ts-ignore
        } else if (state.url[0].path !== 'uwl') {
          // @ts-ignore
          this.path = null;
          // @ts-ignore
          this.committeeId = null;
        }
      });
  }
  setCommitteeName(name: string) {
    this.committeeName.next(name);
  }
  getCommitteeNameValue() {
    return this.committeeName.value;
  }
  setValue(newValue): void {
    if (this.path === 'committees' && this.committeeId !== null) {
      window.location.hash = newValue;
      this.yearForCommitteePage.next(newValue);
    } else {
      window.location.hash = newValue;
      this.currentYear.next(newValue);
    }
  }
  committeeGetValue(): Observable<string> {
    return this.yearForCommitteePage.asObservable();
  }
  getValue(): Observable<string> {
    return this.currentYear.asObservable();
  }
  public get getYearValue(): string {
    return this.currentYear.value;
  }

  public setYears(years: string[]) {
    this.years.next(years);
  }

  public get getYears() {
    return this.years.asObservable();
  }
}
