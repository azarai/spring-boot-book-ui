import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { PaginationModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-bootstrap';
import { CommentlistComponent } from './commentlist.component';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { ConfirmdialogComponent } from '../confirmdialog/confirmdialog.component';

import { CommentService } from '../comment.service';

class MockCommentService {
  public getComments (page:number) {
    return Observable.from([]);
  }
}


describe('CommentlistComponent', () => {
  let component: CommentlistComponent;
  let fixture: ComponentFixture<CommentlistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentlistComponent, ConfirmdialogComponent],
      imports: [ FormsModule,
                 PaginationModule.forRoot(),
                 ModalModule.forRoot()
              ],
      providers :[{ provide: CommentService, useClass: MockCommentService }]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
