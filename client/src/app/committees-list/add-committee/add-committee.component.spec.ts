import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCommitteeComponent } from './add-committee.component';

describe('AddCommitteeComponent', () => {
  let component: AddCommitteeComponent;
  let fixture: ComponentFixture<AddCommitteeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCommitteeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCommitteeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
