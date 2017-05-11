import { Component, OnInit, ViewChild } from '@angular/core';
import { CommentService } from '../comment.service';



@Component({
  selector: 'app-commentlist',
  templateUrl: './commentlist.component.html',
  styleUrls: ['./commentlist.component.css']
})
export class CommentlistComponent implements OnInit { 

  private comments:[any];

  public totalItems:number;
  public currentPage:number;
  public numPages:number;
  public itemsPerPage:number = 5;
  public maxSize:number = 5;

  constructor(private commentService: CommentService) { 
  }

//[class.bg-warning]="comment.spam == true"

  ngOnInit() {
    this.loadData(0);
  }

  loadData(page:number) {
    this.commentService.getComments(page).subscribe(
      data => {
        this.totalItems = data.total_elements;
        this.currentPage =data.number +1 ;
        this.numPages = data.total_pages;
        this.comments = data.content;
      }
    )
  }

  public pageChanged(event:any):void {
    if (this.currentPage !== event.page) {
      this.loadData(event.page -1);
    }
  }

  public deleteConfirmed(comment) {
      this.commentService.deleteComment(comment).subscribe(
      result => {
        this.comments = null;
        this.loadData(this.currentPage);
      },
      error => console.log(error),
    );
  }
}
