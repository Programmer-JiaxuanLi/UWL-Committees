import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDeptComponent } from './add-dept.component';

describe('AddDeptComponent', () => {
  let component: AddDeptComponent;
  let fixture: ComponentFixture<AddDeptComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDeptComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDeptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
