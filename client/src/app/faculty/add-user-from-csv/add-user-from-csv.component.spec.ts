import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserFromCSVComponent } from './add-user-from-csv.component';

describe('AddUserFromCSVComponent', () => {
  let component: AddUserFromCSVComponent;
  let fixture: ComponentFixture<AddUserFromCSVComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUserFromCSVComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserFromCSVComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
