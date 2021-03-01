import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteDeptComponent } from './delete-dept.component';

describe('DeleteDeptComponent', () => {
  let component: DeleteDeptComponent;
  let fixture: ComponentFixture<DeleteDeptComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteDeptComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteDeptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
