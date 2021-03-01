import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateYearComponent } from './create-year.component';

describe('CreateYearComponent', () => {
  let component: CreateYearComponent;
  let fixture: ComponentFixture<CreateYearComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateYearComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateYearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
