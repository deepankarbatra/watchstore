import { TestBed } from '@angular/core/testing';

import { MyAddressesService } from './my-addresses.service';

describe('MyAddressesService', () => {
  let service: MyAddressesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MyAddressesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
