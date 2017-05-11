import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import 'rxjs/add/observable/of';

@Injectable()
export class AuthenticationService {

    private server:string ="http://localhost:8080";

    constructor(private http: Http) { }
 
    login(username: string, password: string) {
      let headers: Headers = new Headers({ 'Content-Type': 'application/json' });
      let authHeaderValue = "Basic " + btoa(username + ":" + password);
      headers.append("Authorization", authHeaderValue);
      let options = new RequestOptions({ headers: headers });
      return this.http.get(this.server + "/authenticate", options).map((response: Response) => {
          if( response.status === 200) {
            localStorage.setItem('currentUser', JSON.stringify({ user: username, token: authHeaderValue}));
          }  
      });

    }
 
    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        return Observable.of(true);
    }
}