import { Injectable } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';

import { Observable, ObservableInput } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class CommentService {

  private server:string ="http://localhost:8080";

  constructor(private http: Http) { 
    
  }


  public getComments (page:number) {
    let username: string = 'admin';
      let password: string = 'mypassword';
      let headers: Headers = new Headers({ 'Content-Type': 'application/json' });
       headers.append("Authorization", "Basic " + btoa(username + ":" + password));
       let options = new RequestOptions({ headers: headers });
    return this.http.get(this.server + "/comments?page=" + page, options).map(this.extractData);
  }

  public deleteComment(comment) {
    let username: string = 'admin';
      let password: string = 'mypassword';
      let headers: Headers = new Headers();
       headers.append("Authorization", "Basic " + btoa(username + ":" + password));
       let options = new RequestOptions({ headers: headers });

     return this.http.delete(this.server + "/" + comment.id, options).map(this.extractData);
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
