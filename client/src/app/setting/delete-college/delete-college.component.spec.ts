import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteCollegeComponent } from './delete-college.component';

describe('DeleteCollegeComponent', () => {
  let component: DeleteCollegeComponent;
  let fixture: ComponentFixture<DeleteCollegeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteCollegeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteCollegeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
