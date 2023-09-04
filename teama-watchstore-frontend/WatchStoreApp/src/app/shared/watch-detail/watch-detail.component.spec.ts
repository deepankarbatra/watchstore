import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WatchDetailComponent } from './watch-detail.component';

describe('WatchDetailComponent', () => {
  let component: WatchDetailComponent;
  let fixture: ComponentFixture<WatchDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WatchDetailComponent]
    });
    fixture = TestBed.createComponent(WatchDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
