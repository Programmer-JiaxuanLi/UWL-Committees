import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {AuthenticationService} from './authentication.service';
import {forkJoin, Observable} from 'rxjs';
import {AppConstants} from '../constants/app-constants';
import {Committee} from '../models/committee';
import {Survey} from '../models/survey';
import {User} from '../models/user';
import {HashedCommittees} from '../models/hashed-committees';
import {Page} from '../models/page';
import {Criteria} from '../models/criteria';
import {Gender} from '../models/gender';
import {Department} from '../models/department';
import {College} from '../models/college';
import {CommitteeSummary} from '../models/committee-summary';
import {SurveyResponse} from '../models/survey-response';

@Injectable({
  providedIn: 'root'
})

export class ApiService {
  constructor( private http: HttpClient, private authService: AuthenticationService) {}

  getCommittees(): Observable<Committee[]> {
    return this.http.get<Committee[]>( `${AppConstants.API_URL}/committees` );
  }

  getCommitteesByYear(year: string): Observable<CommitteeSummary[]> {
      return this.http.get<CommitteeSummary[]>( `${AppConstants.API_URL}/committees?startYear=${year}&endYear=${year}` );
  }

  getHashedCommitteesByYears(startYear: string, endYear: string): Observable<HashedCommittees> {
    return this.http.get<HashedCommittees>( `${AppConstants.API_URL}/committees/hashedCommittees?` +
      `startYear=${startYear}&endYear=${endYear}` );
  }

  getYears(): Observable<string[]> {
    return this.http.get<string[]>(`${AppConstants.API_URL}/settings/years`, {} );
  }

  getCommitteeYears(id: string): Observable<string[]> {
    return this.http.get<string[]>(`${AppConstants.API_URL}/committees/${id}/years`, {} );
  }

  getSurvey(userId: string): Observable<Survey> {
    return this.http.get<Survey>(`${AppConstants.API_URL}/users/${userId}/survey`, { });
  }
  modifySurvey(userId: string, rId: number, surveyResponse: SurveyResponse) {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.put<SurveyResponse>(`${AppConstants.API_URL}/users/${userId}/survey/responses/${rId}`, surveyResponse, config);
  }
  getFacultyByYear(year: string): Observable<Page> {
    return this.http.get<Page>(`${AppConstants.API_URL}/users?year=${year}`);
  }

  getFacultyByYearAndQueries(year: string, queries: any, pageNo: number) {
    let params = Object.keys( queries ).filter(value =>  queries[value].length > 0).reduce(
      (acc, val) => acc = acc.append(val, queries[val] ), new HttpParams() );
    params = params.append( 'year', year );
    params = params.append( 'pageNo', pageNo.toString() );
    return this.http.get<Page>(`${AppConstants.API_URL}/users`, { params } );
  }

  modifyUser(user: User): Observable<User> {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.put<User>(`${AppConstants.API_URL}/users/${user.id}`, user, config);
  }

  deleteUser(userId: string): Observable<any> {
    return this.http.delete(`${AppConstants.API_URL}/users/${userId}`);
  }

  getUserVolunteeredCommittees(userId): Observable<Committee[]> {
    return this.http.get<Committee[]>(`${AppConstants.API_URL}/users/${userId}/enlistings/committees`, {} );
  }

  getUserAssignedCommittees(userId): Observable<Committee[]> {
    return this.http.get<Committee[]>(`${AppConstants.API_URL}/users/${userId}/committees`, {} );
  }

  assignUserToOneCommittee(committeeId, userId): Observable<any> {
    return  this.http.put(`${AppConstants.API_URL}/committees/${committeeId}/members/${userId}`, {} );
  }

  removeUserFromCommittee(committeeId, userId): Observable<User> {
    // @ts-ignore
    return this.http.delete(`${AppConstants.API_URL}/committees/${committeeId}/members/${userId}`);
  }

  getCommitteeMember(committeeId): Observable<User[]> {
    return  this.http.get<User[]>(`${AppConstants.API_URL}/committees/${committeeId}/members`);
  }
  getCommitteeById(committeeId): Observable<Committee> {
    return this.http.get<Committee>(`${AppConstants.API_URL}/committees/${committeeId}`);
  }

  getCommitteeVolunteers(committeeId): Observable<User[]> {
    return this.http.get<User[]>(`${AppConstants.API_URL}/committees/${committeeId}/volunteers`);
  }
  createUser(user: User): Observable<User> {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.post <User> (`${AppConstants.API_URL}/users`, user, config);
  }
  getCommitteeCriteriaStatus(committees: CommitteeSummary[]) {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    const reqs2 = [];
    committees.forEach(
      committee => {
        reqs2.push(this.http.get <number> (`${AppConstants.API_URL}/committees/${committee.id}/unsatisfiedCriteria/size`, config));
      }
    );
    return forkJoin(reqs2);
  }
  uploadFacultiesFromCSV(faculties: User[]) {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    const reqs2 = [];
    // tslint:disable-next-line:prefer-for-of
    faculties.forEach(
      value2 => {
        reqs2.push(this.http.post <User> (`${AppConstants.API_URL}/users`, value2, config));
      }
    );
    return  forkJoin(reqs2);
    // return this.http.post <User> (`${AppConstants.API_URL}/user`, faculties, config);
  }
  createCommittee(newCommittee: Committee) {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    const data = {
      introduction: newCommittee.introduction,
      name: newCommittee.name,
      year: newCommittee.year,
      criteria: newCommittee.criteria,
      duties: newCommittee.duties,
    };
    return this.http.post <User> (`${AppConstants.API_URL}/committees`, data, config);
  }
  updateSurvey(userId: string, committeeId: string, year: string): Observable<Survey> {
    return this.http.post<Survey>(`${AppConstants.API_URL}/users/${userId}/enlistings/${committeeId}?year=${year}`, {} );
  }
  createYear(year: string): Observable<string> {
    return this.http.post <string> (`${AppConstants.API_URL}/settings/years/${year}`, {});
  }

  getCommitteeByYearAndName(year, name): Observable<CommitteeSummary> {
    return this.http.get<CommitteeSummary>(`${AppConstants.API_URL}/committees/${name}/years/${year}`, {} );
  }
  getUserYears(email): Observable<string[]> {
    return this.http.get<string[]>(`${AppConstants.API_URL}/users/email/${email}/years`, {} );
  }
  deleteCommittee(committeeId: string): Observable<Committee> {
    return this.http.delete<Committee>(`${AppConstants.API_URL}/committees/${committeeId}`);
  }
  getUnSatisfiedCriteria(committeeId: string): Observable<Criteria[]> {
    return this.http.get<Criteria[]>( `${AppConstants.API_URL}/committees/${committeeId}/unsatisfiedCriteria`);
  }
  //
  //
  //
  //
  //                     genders
  getGendersByYear(year: string): Observable<Gender[]> {
    return this.http.get<Gender[]>(`${AppConstants.API_URL}/settings/genders?year=${year}`);
  }
  modifyGender(gender: Gender): Observable<Gender> {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.put<Gender>(`${AppConstants.API_URL}/settings/genders/${gender.id}`, gender, config);
  }
  deleteGender(id: string): Observable<any> {
    return this.http.delete<any>(`${AppConstants.API_URL}/settings/genders/${id}`);
  }
  createGender(gender: Gender): Observable<Gender> {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.post<Gender>(`${AppConstants.API_URL}/settings/genders`, gender, config);
  }
  //                     depts
  getDeptByYear(year: string): Observable<Department[]> {
    return this.http.get<Department[]>(`${AppConstants.API_URL}/settings/departments?year=${year}`);
  }
  modifyDept(dept: Department): Observable<Department> {
    return this.http.put<Department>(`${AppConstants.API_URL}/settings/departments/${dept.id}`, dept);
  }
  deleteDept(id: string): Observable<any> {
    return this.http.delete<any>(`${AppConstants.API_URL}/settings/departments/${id}`);
  }
  createDept(dept: Department): Observable<Department> {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.post<Department>(`${AppConstants.API_URL}/settings/departments`, dept, config);
  }

  //                     colleges
  getCollegeByYear(year: string): Observable<College[]> {
    return this.http.get<College[]>(`${AppConstants.API_URL}/settings/colleges?year=${year}`);
  }
  getCollegeByYears(startYear: string, endYear: string): Observable<College[]> {
    return this.http.get<College[]>(`${AppConstants.API_URL}/settings/colleges/years?startYear=${startYear}&endYear=${endYear}`);
  }
  modifyCollege(college: College): Observable<College> {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.put<College>(`${AppConstants.API_URL}/settings/colleges/${college.id}`, college, config);
  }
  deleteCollege(id: string): Observable<any> {
    return this.http.delete<any>(`${AppConstants.API_URL}/settings/colleges/${id}`);
  }
  createCollege(college: College): Observable<College> {
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.post<College>(`${AppConstants.API_URL}/settings/colleges`, college, config);
  }
}
