import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteGenComponent } from './delete-gen.component';

describe('DeleteGenComponent', () => {
  let component: DeleteGenComponent;
  let fixture: ComponentFixture<DeleteGenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteGenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteGenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
