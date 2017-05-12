import { Injectable } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';

import { Observable, ObservableInput } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class CommentService {

  private server:string ="/api";

  constructor(private http: Http) { 
    
  }


  public getComments (page:number) {
    return this.http.get(this.server + "/comments?page=" + page).map(this.extractData);
  }

  public deleteComment(comment) {
     return this.http.delete(this.server + "/" + comment.id).map(this.extractData);
  }

  private extractData(res: Response) {
    let content = res.text();
    if(content === undefined || content === null || content.length === 0 ){
      return {};
    }
    let body = res.json();
    return body || {};
  }
}
