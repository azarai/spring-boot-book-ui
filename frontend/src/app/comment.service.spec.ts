import { TestBed, inject } from '@angular/core/testing';
import {
  HttpModule,
  Http,
  Response,
  ResponseOptions,
  XHRBackend
} from '@angular/http';
import { MockBackend } from '@angular/http/testing';

import { CommentService } from './comment.service';

describe('CommentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [CommentService, { provide: XHRBackend, useClass: MockBackend }]
    });
  });

  it('should ...', inject([CommentService], (service: CommentService) => {
    expect(service).toBeTruthy();
  }));
});
