import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryStatusComponent } from './inventory-status.component';

describe('InventoryStatusComponent', () => {
  let component: InventoryStatusComponent;
  let fixture: ComponentFixture<InventoryStatusComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InventoryStatusComponent]
    });
    fixture = TestBed.createComponent(InventoryStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
