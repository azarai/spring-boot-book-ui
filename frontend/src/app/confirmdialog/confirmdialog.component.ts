import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
  selector: 'confirmdialog',
  templateUrl: './confirmdialog.component.html',
  styleUrls: ['./confirmdialog.component.css']
})
export class ConfirmdialogComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal:ModalDirective;
  
  @Input()
  public confirmed: Function;

  private comment:any;
  constructor(  ) { }

  ngOnInit() {
  }

  public confirmDelete(comment:any) {
    this.comment = comment;
    this.confirmModal.show();
  }

  public confirmedDelete(){
    console.log(this.comment);
    this.confirmed(this.comment);
    this.confirmModal.hide();
  }
}
