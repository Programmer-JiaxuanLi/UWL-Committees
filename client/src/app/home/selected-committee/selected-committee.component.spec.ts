import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectedCommitteeComponent } from './selected-committee.component';

describe('SelectedCommitteeComponent', () => {
  let component: SelectedCommitteeComponent;
  let fixture: ComponentFixture<SelectedCommitteeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectedCommitteeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectedCommitteeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
