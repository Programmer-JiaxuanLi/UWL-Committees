import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GetUserCommitteesComponent } from './get-user-committees.component';

describe('GetUserCommitteesComponent', () => {
  let component: GetUserCommitteesComponent;
  let fixture: ComponentFixture<GetUserCommitteesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetUserCommitteesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GetUserCommitteesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
