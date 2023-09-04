import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddWatchComponent } from './add-watch.component';

describe('AddWatchComponent', () => {
  let component: AddWatchComponent;
  let fixture: ComponentFixture<AddWatchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddWatchComponent]
    });
    fixture = TestBed.createComponent(AddWatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
