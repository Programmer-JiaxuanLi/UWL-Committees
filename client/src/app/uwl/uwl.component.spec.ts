import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UWLComponent } from './uwl.component';

describe('UWLComponent', () => {
  let component: UWLComponent;
  let fixture: ComponentFixture<UWLComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UWLComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UWLComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
