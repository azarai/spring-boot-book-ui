import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ConfirmdialogComponent } from './confirmdialog.component';

import { ModalModule } from 'ngx-bootstrap';


describe('ConfirmdialogComponent', () => {
  let component: ConfirmdialogComponent;
  let fixture: ComponentFixture<ConfirmdialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmdialogComponent ],
      imports: [ModalModule.forRoot()]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmdialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show', () => {
    let comment = {comment : "a comment"};
    
    component.confirmDelete(comment);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('div[class=modal-body]').textContent).toContain(comment.comment);
  });



});
