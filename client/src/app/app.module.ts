import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';


import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import {RouterModule} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {Interceptor} from './utilities/interceptor';
import { SlideBarComponent } from './slide-bar/slide-bar.component';
import { FacultyComponent } from './faculty/faculty.component';
import { CommitteesDetailsComponent } from './committees-details/committees-details.component';
import { CommitteesListComponent } from './committees-list/committees-list.component';
import { ReportComponent } from './report/report.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {GoogleChartsModule} from 'angular-google-charts';
import { UWLComponent } from './uwl/uwl.component';
import { DeleteUserComponent } from './faculty/delete-user/delete-user.component';
import { ModifyUserComponent } from './faculty/modify-user/modify-user.component';
import { AddUserComponent } from './faculty/add-user/add-user.component';
import { AddUserFromCSVComponent } from './faculty/add-user-from-csv/add-user-from-csv.component';
import { RemoveMemberComponent } from './committees-details/remove-member/remove-member.component';
import { AssignMemberComponent } from './committees-details/assign-member/assign-member.component';
import { AddCommitteeComponent } from './committees-list/add-committee/add-committee.component';
import { SelectedCommitteeComponent } from './home/selected-committee/selected-committee.component';
import { SettingsComponent } from './setting/settings.component';
import { AddDeptComponent } from './setting/add-dept/add-dept.component';
import { AddCollegeComponent } from './setting/add-college/add-college.component';
import { AddGenderComponent } from './setting/add-gender/add-gender.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { FooterComponent } from './footer/footer.component';
import { GetUserCommitteesComponent } from './faculty/get-user-committees/get-user-committees.component';
import { CreateYearComponent } from './slide-bar/create-year/create-year.component';
import { EditCollegeComponent } from './setting/edit-college/edit-college.component';
import { EditGenderComponent } from './setting/edit-gender/edit-gender.component';
import { EditDeptComponent } from './setting/edit-dept/edit-dept.component';
import { DeleteGenComponent } from './setting/delete-gen/delete-gen.component';
import { DeleteCollegeComponent } from './setting/delete-college/delete-college.component';
import { DeleteDeptComponent } from './setting/delete-dept/delete-dept.component';

@NgModule({

  imports: [
    GoogleChartsModule,
    NgbModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    AppRoutingModule,
    RouterModule,
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    FontAwesomeModule
  ],

  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SlideBarComponent,
    FacultyComponent,
    CommitteesDetailsComponent,
    CommitteesListComponent,
    ReportComponent,
    UWLComponent,
    DeleteUserComponent,
    ModifyUserComponent,
    AddUserComponent,
    AddUserFromCSVComponent,
    RemoveMemberComponent,
    AssignMemberComponent,
    AddCommitteeComponent,
    SelectedCommitteeComponent,
    SettingsComponent,
    AddDeptComponent,
    AddCollegeComponent,
    AddGenderComponent,
    TopBarComponent,
    FooterComponent,
    GetUserCommitteesComponent,
    CreateYearComponent,
    EditCollegeComponent,
    EditGenderComponent,
    EditDeptComponent,
    DeleteGenComponent,
    DeleteCollegeComponent,
    DeleteDeptComponent,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
