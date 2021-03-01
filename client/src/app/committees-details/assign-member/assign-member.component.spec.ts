import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignMemberComponent } from './assign-member.component';

describe('AssignMemberComponent', () => {
  let component: AssignMemberComponent;
  let fixture: ComponentFixture<AssignMemberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignMemberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
