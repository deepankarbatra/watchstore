import { TestBed } from '@angular/core/testing';

import { WatchResolverService } from './watch-resolver.service';

describe('WatchResolverService', () => {
  let service: WatchResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WatchResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
